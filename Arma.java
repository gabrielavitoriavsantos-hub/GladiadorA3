package model;

public class Arma {

    private String tipo;
    private int forca;

    public Arma(String tipo) {
        this.tipo = tipo.toLowerCase();

        switch (this.tipo) {
            case "espada": forca = 5; break;
            case "lança":  forca = 4; break;
            case "arco":
            case "flecha": forca = 3; break;
            default: forca = 1; break;
        }
    }

    public String getTipo() { return tipo; }
    public int getForca() { return forca; }
}
