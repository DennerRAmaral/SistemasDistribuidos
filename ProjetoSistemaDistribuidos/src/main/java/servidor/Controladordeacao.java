package servidor;

public class Controladordeacao {
    private String action;

    public Controladordeacao(String action) {
        this.action = action;
    }
    
    public String seletor(){
        return this.action;
    }
    
    
}
