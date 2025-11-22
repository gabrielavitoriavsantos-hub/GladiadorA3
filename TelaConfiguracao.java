package ui;

import javax.swing.*;
import java.awt.*;
import model.*;

public class TelaConfiguracao extends JPanel {

    private Arena arena;

    public TelaConfiguracao(JFrame frame, Gladiador[] gladiadores, Arena arena) {
        this.arena = arena;

        setLayout(new GridBagLayout());
        setBackground(new Color(45, 32, 18)); // marrom medieval

        // Painel central com moldura medieval
        JPanel box = new JPanel(new GridBagLayout());
        box.setBackground(new Color(70, 55, 35));
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 100, 60), 4),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        // ============================
        //          TÍTULO
        // ============================
        JLabel titulo = new JLabel("⚔️ Configuração dos Gladiadores ⚔️", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 26));
        titulo.setForeground(new Color(255, 230, 190));

        g.gridx = 0; g.gridy = 0;
        g.gridwidth = 2;
        box.add(titulo, g);

        g.gridwidth = 1;

        // ============================
        //      GLADIADOR 1
        // ============================
        JLabel lbl1 = mkLabel("Nome do Gladiador 1:");
        JTextField nome1 = mkField();

        JLabel lblArma1 = mkLabel("Arma:");
        String[] armas = {"Espada", "Lança", "Arco"};
        JComboBox<String> arma1 = new JComboBox<>(armas);

        JCheckBox arm1 = mkCheck("Armadura");

        g.gridy = 1; g.gridx = 0; box.add(lbl1, g);
        g.gridx = 1; box.add(nome1, g);

        g.gridy = 2; g.gridx = 0; box.add(lblArma1, g);
        g.gridx = 1; box.add(arma1, g);

        g.gridy = 3; g.gridx = 1; box.add(arm1, g);

        // ============================
        //      GLADIADOR 2
        // ============================
        JLabel lbl2 = mkLabel("Nome do Gladiador 2:");
        JTextField nome2 = mkField();

        JLabel lblArma2 = mkLabel("Arma:");
        JComboBox<String> arma2 = new JComboBox<>(armas);

        JCheckBox arm2 = mkCheck("Armadura");

        g.gridy = 4; g.gridx = 0; box.add(lbl2, g);
        g.gridx = 1; box.add(nome2, g);

        g.gridy = 5; g.gridx = 0; box.add(lblArma2, g);
        g.gridx = 1; box.add(arma2, g);

        g.gridy = 6; g.gridx = 1; box.add(arm2, g);

        // ============================
        //        TORCEDORES
        // ============================
        JLabel lblTorcedores = mkLabel("Torcedores iniciais:");
        JTextField torcedores = mkField("");

        g.gridy = 7; g.gridx = 0; box.add(lblTorcedores, g);
        g.gridx = 1; box.add(torcedores, g);

        // ============================
        //         BOTÃO
        // ============================
        JButton iniciar = mkButton("Entrar na Arena ⚔️");

        g.gridy = 8; g.gridx = 0; g.gridwidth = 2;
        box.add(iniciar, g);

        // Colocar o painel central na tela
        add(box);

        // ============================
        //      LÓGICA DO BOTÃO
        // ============================
        iniciar.addActionListener(e -> {

            gladiadores[0] = new Gladiador(
                    nome1.getText(),
                    new Arma(arma1.getSelectedItem().toString()),
                    arm1.isSelected()
            );

            gladiadores[1] = new Gladiador(
                    nome2.getText(),
                    new Arma(arma2.getSelectedItem().toString()),
                    arm2.isSelected()
            );

            Arena a = (this.arena != null) ?
                    this.arena :
                    new Arena(Integer.parseInt(torcedores.getText()));

            frame.setContentPane(new TelaArena(frame, gladiadores, a));
            frame.revalidate();
        });
    }

    // ============================
    //  ESTILIZAÇÃO – MÉTODOS
    // ============================

    private JLabel mkLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Serif", Font.BOLD, 18));
        l.setForeground(new Color(255, 235, 200));
        return l;
    }

    private JTextField mkField() {
        return mkField("");
    }

    private JTextField mkField(String txt) {
        JTextField t = new JTextField(txt);
        t.setBackground(new Color(90, 70, 50));
        t.setForeground(Color.WHITE);
        t.setCaretColor(Color.WHITE);
        t.setBorder(BorderFactory.createLineBorder(new Color(140, 120, 80), 2));
        return t;
    }

    private JCheckBox mkCheck(String txt) {
        JCheckBox c = new JCheckBox(txt);
        c.setForeground(new Color(255, 230, 190));
        c.setOpaque(false);
        return c;
    }

    private JButton mkButton(String txt) {
        JButton b = new JButton(txt);
        b.setFont(new Font("Serif", Font.BOLD, 18));
        b.setBackground(new Color(120, 90, 50));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createLineBorder(new Color(200, 170, 100), 3));
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(200, 40));
        return b;
    }
}


