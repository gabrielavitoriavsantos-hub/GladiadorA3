package model;

public class Arena {

    private int torcedores;
    private int humor = 50; // 0 = tristes, 100 = eufóricos

    public Arena(int torcedores) {
        this.torcedores = torcedores;
    }

    // Ajusta humor durante o combate
    public void ajustarHumor(int valor) {
        humor += valor;
        if (humor < 0) humor = 0;
        if (humor > 100) humor = 100;
    }

    // Atualiza humor na rodada (pode expandir depois)
    public void atualizarHumorDepoisDoTurno() {}

    // Atualiza humor ao final do combate
    public void atualizarTorcidaFinal(Gladiador g1, Gladiador g2) {
        if (g1.estaVivo()|| g2.estaVivo()) {
            // VITÓRIA — torcida feliz
            humor = 90;
            torcedores += (int)(torcedores * 0.10);
        } else 
            // EMPATE — torcida triste
            humor = 20;
            torcedores -= (int)(torcedores * 0.10);
        }
    

    public String getHumorStatus() {
        if (humor >= 70) return "Eufóricos 🎉";
        if (humor >= 40) return "Empolgados 👏";
        return "Tristes 😢";
    }

    public int getHumor() {
        return humor;
    }

    public int getTorcedores() {
        return torcedores;
    }

    public void atualizarHumorPorCombate(Gladiador g1, Gladiador g2) {

    // Quando ambos sobrevivem → tristeza e -25% torcedores
    if (g1.estaVivo() || g2.estaVivo()) {
        torcedores += (int)(torcedores * 0.10);
        humor = 90; // felizes
    }

    // Quando um morre → felicidade e +10% torcedores
    else {torcedores -= (int)(torcedores * 0.25);
        if (torcedores < 0) torcedores = 0;
        humor = 20; // tristeza 
    }
}

}
