import antlr4.CNFConverter;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class CNFTest {

    CNFConverter converter;

    public void implicationToCNFTest() {
        converter = new CNFConverter();

        List<String> cnfs = converter.convertToCNF("P => Q");
        String result = cnfs.get(0);
    }

}
