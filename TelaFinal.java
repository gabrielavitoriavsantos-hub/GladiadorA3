package ui;

import javax.swing.*;
import java.awt.*;
import model.*;
import javax.sound.sampled.Clip;

public class TelaFinal extends JPanel {

    private Clip somVitoria;

    public TelaFinal(JFrame frame, Gladiador g1, Gladiador g2, Arena arena) {

        setLayout(new BorderLayout());
        setBackground(new Color(50, 35, 20));

        // ===== Título =====
        JLabel titulo = new JLabel("🏆 Resultado da Batalha 🏆", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 30));
        titulo.setForeground(new Color(240, 220, 180));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);

        // ===== Vitória / Empate =====
        String resultado;
        if (g1.estaVivo() && !g2.estaVivo()) resultado = g1.getNome() + " VENCEU!";
        else if (!g1.estaVivo() && g2.estaVivo()) resultado = g2.getNome() + " VENCEU!";
        else resultado = "Empate!";

        JLabel lblResultado = new JLabel(resultado, SwingConstants.CENTER);
        lblResultado.setFont(new Font("Serif", Font.BOLD, 50));
        lblResultado.setForeground(new Color(250, 230, 190));

        // ===== Status da Torcida =====
        JLabel lblHumor = new JLabel(
            "Humor da Torcida: " + arena.getHumorStatus(),
            SwingConstants.CENTER
        );
        lblHumor.setFont(new Font("Serif", Font.PLAIN, 20));
        lblHumor.setForeground(new Color(230, 210, 165));

        // Painel central
        JPanel center = new JPanel(new GridLayout(2, 1));
        center.setOpaque(false);
        center.add(lblResultado);
        center.add(lblHumor);

        add(center, BorderLayout.CENTER);

        // ===== Botão voltar =====
        JButton voltar = new JButton("Voltar ao Início");
        voltar.setFont(new Font("Serif", Font.BOLD, 16));
        voltar.setBackground(new Color(245, 235, 200));
        voltar.setForeground(new Color(40, 18, 6));
        voltar.setBorder(BorderFactory.createLineBorder(new Color(150,120,70), 2));

        voltar.addActionListener(e -> {
            frame.setContentPane(new TelaInicial(() -> {
                frame.setContentPane(new TelaConfiguracao(
                    frame,
                    new Gladiador[2],
                    null
                ));
                frame.revalidate();
            }));
            frame.revalidate();
        });

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(voltar);

        add(bottom, BorderLayout.SOUTH);

        // ===== Som de vitória =====
         {
            Clip somVitoria = Som.tocar("vitoria.wav");
            somVitoria.stop();
        }
    }
}
