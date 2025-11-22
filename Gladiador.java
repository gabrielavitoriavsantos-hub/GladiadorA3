package model;

public class Gladiador {

    private String nome;
    private Arma arma;
    private boolean temArmadura;
    private int vidaAtual;
    private final int VIDA_MAX = 20;

    public Gladiador(String nome, Arma arma, boolean temArmadura) {
        this.nome = nome;
        this.arma = arma;
        this.temArmadura = temArmadura;
        this.vidaAtual = VIDA_MAX;
    }

    public void receberGolpe(int dano) {
        if (temArmadura) dano -= 2;
        if (dano < 0) dano = 0;

        vidaAtual -= dano;
        if (vidaAtual < 0) vidaAtual = 0;
    }

    public void reset() {
        vidaAtual = VIDA_MAX;
    }

    public boolean estaVivo() {
        return vidaAtual > 0;
    }

    public String getNome() { return nome; }
    public Arma getArma() { return arma; }
    public int getVidaAtual() { return vidaAtual; }
    public int getVidaMax() { return VIDA_MAX; }
}
