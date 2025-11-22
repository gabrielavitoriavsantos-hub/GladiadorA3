package ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import model.*;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

public class TelaArena extends JPanel {

    private Gladiador g1, g2;
    private Arena arena;
    private LifeBar life1, life2;
    private JLabel img1, img2;
    private JLabel statusTorcida;
    private Clip temaClip;

    public TelaArena(JFrame frame, Gladiador[] gladiadores, Arena arena) {

        this.g1 = gladiadores[0];
        this.g2 = gladiadores[1];
        this.arena = arena;

        setLayout(new BorderLayout());
        setBackground(new Color(45, 32, 20));

        // ====================== TOPO ======================
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel title = new JLabel("⚔ Arena GladiAmo ⚔", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(240, 220, 170));
        top.add(title, BorderLayout.NORTH);

        statusTorcida = new JLabel(
            "Torcida: " + arena.getHumorStatus() + " | Torcedores: " + arena.getTorcedores(),
            SwingConstants.CENTER
        );
        statusTorcida.setFont(new Font("Serif", Font.BOLD, 16));
        statusTorcida.setForeground(new Color(235, 210, 165));

        top.add(statusTorcida, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        // ====================== CENTRO ======================
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);

        img1 = new JLabel();
        img2 = new JLabel();
        setImageLabel(img1, "/assets/gladiador1.png", 220, 300);
        setImageLabel(img2, "/assets/gladiador2.png", 220, 300);

        life1 = new LifeBar(g1.getVidaMax());
        life2 = new LifeBar(g2.getVidaMax());

        JPanel arenaPreview = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(60, 40, 22));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        arenaPreview.setPreferredSize(new Dimension(320, 320));
        arenaPreview.setBorder(BorderFactory.createLineBorder(new Color(120,90,50), 3));

        c.gridx = 0; c.gridy = 0; center.add(img1, c);
        c.gridx = 1; center.add(arenaPreview, c);
        c.gridx = 2; center.add(img2, c);

        c.gridx = 0; c.gridy = 1; center.add(new JLabel(g1.getNome(), SwingConstants.CENTER), c);
        c.gridx = 2; center.add(new JLabel(g2.getNome(), SwingConstants.CENTER), c);

        c.gridx = 0; c.gridy = 2; center.add(life1, c);
        c.gridx = 2; center.add(life2, c);

        add(center, BorderLayout.CENTER);

        // ====================== BOTTOM (LOG + BOTÕES) ======================
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        JTextArea log = new JTextArea(6, 50);
        log.setEditable(false);
        log.setBackground(new Color(235, 220, 190));
        log.setForeground(new Color(40, 20, 10));
        log.setFont(new Font("Serif", Font.PLAIN, 14));

        JScrollPane sp = new JScrollPane(log);
        bottom.add(sp, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        controls.setOpaque(false);

        JButton atk1 = mkPergaminhoButton("Atacar (" + g1.getNome() + ")");
        JButton atk2 = mkPergaminhoButton("Atacar (" + g2.getNome() + ")");
        JButton reset = mkPergaminhoButton("Reiniciar");
        JButton btnTema = mkPergaminhoButton("Play/Pause Música");
        JButton auto = mkPergaminhoButton("⚙ Modo Automático");

        controls.add(atk1);
        controls.add(atk2);
        controls.add(reset);
        controls.add(btnTema);
        controls.add(auto);

        bottom.add(controls, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);

        // ====================== SONS ======================
        temaClip = Som.tocar("tema_medieval.wav");

        // ====================== AÇÕES MANUAIS ======================

        atk1.addActionListener(e -> atacar(g1, g2, life2, log, frame));
        atk2.addActionListener(e -> atacar(g2, g1, life1, log, frame));

        reset.addActionListener(e -> {
            g1.reset();
            g2.reset();
            life1.animarPara(g1.getVidaAtual());
            life2.animarPara(g2.getVidaAtual());
            log.append("--- Jogo reiniciado ---\n");
        });

        btnTema.addActionListener(e -> {
            if (temaClip.isRunning()) temaClip.stop();
            else temaClip.loop(Clip.LOOP_CONTINUOUSLY);
        });

        // ========================== MODO AUTOMÁTICO ==========================
        final int[] turno = {0};

        auto.addActionListener(e -> {
            auto.setEnabled(false);
            atk1.setEnabled(false);
            atk2.setEnabled(false);

            Timer t = new Timer(1200, ev -> {
                if (!g1.estaVivo() || !g2.estaVivo()) {
                    ((Timer) ev.getSource()).stop();
                    checarFim(frame, log);
                    return;
                }

                Gladiador atacante = (turno[0] % 2 == 0) ? g1 : g2;
                Gladiador alvo     = (turno[0] % 2 == 0) ? g2 : g1;

                atacar(atacante, alvo,
                    (atacante == g1 ? life2 : life1),
                    log, frame
                );

                turno[0]++;
            });

            t.start();
        });
    }

    // ========================== MÉTODOS AUXILIARES ==========================

    private void atacar(Gladiador at, Gladiador alvo, LifeBar barra, JTextArea log, JFrame frame) {
        int dano = at.getArma().getForca();
        boolean erro = Math.random() < 0.05;

        if (erro) {
            log.append(at.getNome() + " errou o ataque!\n");
            Som.tocar("erro.wav");
        } else {
            alvo.receberGolpe(dano);
            Som.tocar("golpe.wav");
            log.append(at.getNome() + " causou " + dano + " de dano!\n");
        }

        arena.atualizarHumorPorCombate(g1, g2);
        atualizarTorcida();

        barra.animarPara(alvo.getVidaAtual());

        checarFim(frame, log);
    }

    private void atualizarTorcida() {
        statusTorcida.setText(
            "Torcida: " + arena.getHumorStatus() +
            " | Torcedores: " + arena.getTorcedores()
        );
    }

    private JButton mkPergaminhoButton(String txt) {
        JButton b = new JButton(txt);
        b.setBackground(new Color(245, 235, 200));
        b.setForeground(new Color(40, 18, 6));
        b.setBorder(BorderFactory.createLineBorder(new Color(150, 120, 70), 2));
        b.setFont(new Font("Serif", Font.BOLD, 14));
        return b;
    }

    private void setImageLabel(JLabel lbl, String path, int w, int h) {
        try {
            URL url = getClass().getResource(path);
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            lbl.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            lbl.setText("Imagem não encontrada");
            lbl.setForeground(Color.WHITE);
        }
    }

    private void checarFim(JFrame frame, JTextArea log) {
        if (!g1.estaVivo() || !g2.estaVivo()) {

            String vencedor = g1.estaVivo() ? g1.getNome() :
                              g2.estaVivo() ? g2.getNome() :
                              "Empate";

            log.append("+++ " + vencedor + " venceu! +++\n");

            Som.tocar("vitoria.wav");

            frame.setContentPane(new TelaFinal(frame, g1, g2, arena));
            frame.revalidate();
        }
    }
}
