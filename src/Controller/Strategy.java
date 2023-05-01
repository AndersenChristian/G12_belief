package Controller;

public enum Strategy {
    TRUST_NEW, TRUST_OLD, TRUST_LONG, TRUST_SHORT;

    public static Strategy getStrategy(int i){
        switch (i){
            case 0 -> {
                return TRUST_NEW;
            }
            case 1 -> {
                return TRUST_OLD;
            }
            case 2 -> {
                return TRUST_LONG;
            }
            case 3 -> {
                return TRUST_SHORT;
            }
        }
        return TRUST_NEW;
    }
}
