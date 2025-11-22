

import javax.swing.*;
import model.Gladiador;
import ui.TelaInicial;
import ui.TelaConfiguracao;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("GladiAmo – Jogo");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(new TelaInicial(() -> {
            frame.setContentPane(new TelaConfiguracao(frame, new Gladiador[2], null));
            frame.revalidate();
        }));

        frame.setVisible(true);
    }
}

