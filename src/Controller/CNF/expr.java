package Controller.CNF;

abstract class expr{}

class Solo extends expr{
    public String var;
    public Solo(String var){
        this.var = var;
    }
}

class Equal extends expr{
    public String var1;
    public String var2;
    public Equal(String var1, String var2){
        this.var1 = var1; this.var2 = var2;
    }
}

class Implies extends expr{
    public String var1;
    public String var2;
    public Implies(String var1, String var2){
        this.var1 = var1; this.var2 = var2;
    }
}