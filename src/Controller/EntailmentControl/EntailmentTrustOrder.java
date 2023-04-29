package Controller.EntailmentControl;

import Model.Data;
import Model.IKnowledgeBase;
import Model.KnowledgeBase;
import Model.Operator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class EntailmentTrustOrder implements IEntailmentCheck {
    boolean logEnabled = false;

    /**
     * This method need access to the actual IKnowledgeBase, since it will remove entailments.
     *
     * Giving a copy would not imply the changes to the actual IKnowledgeBase.
     *
     * @param data the implemented knowledgebase. It is expected that the claus in the knowledge base is sorted, so that B|A would never happen, but instead would be A|B
     */
    @Override
    public void removeEntailments(IKnowledgeBase data) {
        AtomicInteger bestResult = new AtomicInteger(Integer.MAX_VALUE);
        Arrays.stream(
                data.getDataAtIndex(0)
                        .getClaus()
                        .split("["+ Operator.OR.getOperator() +"]"))
                .parallel() //maybe possible to parallel - ignore until working in single threat
                .forEach(s -> {
                    int result = bestResult(s, new KnowledgeBase(data),1, 0);
                    if (result < bestResult.get()) bestResult.set(result);
                });

        LOG("after return");
        LOG(bestResult.toString());

        //convert the best result into a binary.
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(bestResult.get()));
        LOG(binary.toString());

        binary.reverse();
        LOG(binary.toString());

        //forcing the length of the binary number to match the size of our belief set
        for(int i = 0; i < data.getSize() -binary.length(); i++) {
            binary.insert(0,"0");
        }
        LOG(binary.toString());
        LOG(String.valueOf(binary.length()));

        Data[] clausToRemove = IntStream.range(0, binary.length())
                .filter(i -> binary.charAt(i) == '1')
                .mapToObj(data::getDataAtIndex)
                .toArray(Data[]::new);

        for(Data d: clausToRemove){
            LOG(d.getClaus());
        }

        Arrays.stream(clausToRemove)
                .forEach(data::removeData);
    }

    private int bestResult(String believe, IKnowledgeBase copyData, int dept, int currentDeleteValue){
        //TODO: Remove previous claus from Copy data
        copyData.removeDataAtIndex(0);

        LOG("believe: " + believe + "\n Data:");
        Arrays.stream(copyData.getAllData())
                .forEach(d -> LOG(d.getClaus()));
        LOG("");

        //TODO: remove believe from rest of data.
        String invertedBelieve = believe.length() == 1 ? Operator.NOT.getOperator() + believe : String.valueOf(believe.charAt(1));

        (Arrays.stream(copyData.getAllData()))
                .forEach(d -> {
                            //remove all states that contains our belief (those are trusted regardless of what happens next)
                            if (d.getClaus().contains(believe) && !d.getClaus().contains(invertedBelieve)) {
                                copyData.removeData(d);
                                return;
                            }

                            //If it wasn't remove, we try to remove the inverted version.
                            d.setClaus(d.getClaus().replace(invertedBelieve, ""));
                            //out of bounce control
                            if(d.getClaus().length() == 0) return;
                            //removes any undesired OR operators
                            d.setClaus(d.getClaus().replace(
                                    "[" + Operator.OR.getOperator() + Operator.OR.getOperator() + "]|", //remove 2 Or in a row
                                    Operator.OR.getOperator()));
                            //check if the first char in the string is an OR operator, and removes it
                            d.setClaus(d.getClaus().charAt(0) == Operator.OR.getOperator().charAt(0) ? d.getClaus().substring(1) : d.getClaus());
                            //check if the last char in the string is an OR operator and remove it
                            d.setClaus(d.getClaus().charAt(d.getClaus().length() - 1) == Operator.OR.getOperator().charAt(0) ? d.getClaus().substring(0, d.getClaus().length() - 2) : d.getClaus());
                        });


        //Check if any claus are empty (false)
        int[] indexToRemove = IntStream.range(0, copyData.getSize())
                .filter(i -> copyData.getDataAtIndex(i).getClaus().isEmpty())
                .toArray();

        Arrays.stream(
                Arrays.stream(indexToRemove)
                        .mapToObj(copyData::getDataAtIndex)
                        .toArray(Data[]::new))
                .forEach(copyData::removeData);
        for(int i: indexToRemove){
            currentDeleteValue += Math.pow(2,i+dept);
        }

        if(copyData.getAllData().length == 0) return currentDeleteValue;

        AtomicInteger bestResult = new AtomicInteger(Integer.MAX_VALUE);
        //now continue with the next claus.
        int finalCurrentDeleteValue = currentDeleteValue;
        Arrays.stream(
                        copyData.getDataAtIndex(0)
                                .getClaus()
                                .split("["+ Operator.OR.getOperator() +"]"))
                .parallel() //maybe possible to parallel - ignore until working in single threat
                .forEach(s -> {
                    int result = bestResult(s, new KnowledgeBase(copyData),dept, finalCurrentDeleteValue);
                    if (result < bestResult.get()) bestResult.set(result);
                });

        return bestResult.get();
    }

    public void enableLOG(boolean b){
        logEnabled = b;
    }

    private void LOG(String message){
        if (this.logEnabled) System.out.println(message);
    }

    private void LOG(String[] message){
        if (this.logEnabled) System.out.print(Arrays.toString(message));
    }
}
