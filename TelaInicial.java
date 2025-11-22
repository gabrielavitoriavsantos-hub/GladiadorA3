package ui;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JPanel {

    public TelaInicial(Runnable iniciarJogo) {
        setLayout(new BorderLayout());
        setBackground(new Color(50, 35, 20));

        JLabel titulo = new JLabel("GLADIAMO ⚔", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 50));
        titulo.setForeground(new Color(235, 215, 170));
        titulo.setBorder(BorderFactory.createEmptyBorder(30,10,10,10));

        JPanel botoes = new JPanel();
        botoes.setOpaque(false);

        JButton btnIniciar = new JButton("Iniciar Jogo");
        btnIniciar.addActionListener(e -> iniciarJogo.run());

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));

        botoes.add(btnIniciar);
        botoes.add(btnSair);

        add(titulo, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
    }
}



