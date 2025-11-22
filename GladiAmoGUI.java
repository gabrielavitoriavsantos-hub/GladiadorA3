
// GladiAmoGUI.java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class GladiAmoGUI {
    private JFrame frame;
    private JLabel lblG1Img, lblG2Img;
    private JProgressBar pbG1Vida, pbG2Vida;
    private JTextArea taLog;
    private JLabel lblHumor;
    private Arena arena;
    private Gladiador g1, g2;
    private JButton btnAtacar1, btnAtacar2, btnReset, btnAuto;
    private Timer animaTimer;
    private boolean autoMode = false;

    public GladiAmoGUI() {
        criarModelo();
        initialize();
    }

    private void criarModelo() {
        // armas de exemplo
        Arma espada = new Arma("Espada curta", 8, 15);
        Arma lanca = new Arma("Lança", 10, 10);

        // gladiadores exemplo
        g1 = new Gladiador("Maximus", 5, true, espada, 10);
        g2 = new Gladiador("Cassius", 5, false, lanca, 5);

        arena = new Arena(g1, g2);
    }

    private void initialize() {
        frame = new JFrame("GladiAmo - Arena Interativa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(980, 680);
        frame.setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(8, 8, 8, 8));
        frame.setContentPane(content);

        // Top: título e humor
        JPanel top = new JPanel(new BorderLayout());
        JLabel title = new JLabel("GladiAmo - O Grande Duelo", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        top.add(title, BorderLayout.CENTER);
        lblHumor = new JLabel("Humor da Arena: " + arena.avaliarHumor(), SwingConstants.RIGHT);
        lblHumor.setFont(new Font("SansSerif", Font.ITALIC, 14));
        top.add(lblHumor, BorderLayout.EAST);
        content.add(top, BorderLayout.NORTH);

        // Center: painel da arena visual
        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));
        center.setBackground(Color.DARK_GRAY);

        // Gladiador 1 painel
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBackground(Color.BLACK);
        lblG1Img = new JLabel();
        lblG1Img.setHorizontalAlignment(SwingConstants.CENTER);
        lblG1Img.setPreferredSize(new Dimension(280, 300));
        setImage(lblG1Img, "/resources/gladiador1.png"); // ajustar caminho
        p1.add(lblG1Img, BorderLayout.CENTER);

        pbG1Vida = new JProgressBar(0, g1.getVidaMax());
        pbG1Vida.setValue(g1.getVidaAtual());
        pbG1Vida.setStringPainted(true);
        pbG1Vida.setString(g1.getNome() + " - Vida");
        p1.add(pbG1Vida, BorderLayout.SOUTH);

        // Gladiador 2 painel
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBackground(Color.BLACK);
        lblG2Img = new JLabel();
        lblG2Img.setHorizontalAlignment(SwingConstants.CENTER);
        lblG2Img.setPreferredSize(new Dimension(280, 300));
        setImage(lblG2Img, "/resources/gladiador2.png");
        p2.add(lblG2Img, BorderLayout.CENTER);

        pbG2Vida = new JProgressBar(0, g2.getVidaMax());
        pbG2Vida.setValue(g2.getVidaAtual());
        pbG2Vida.setStringPainted(true);
        pbG2Vida.setString(g2.getNome() + " - Vida");
        p2.add(pbG2Vida, BorderLayout.SOUTH);

        // Central: imagem da arena / efeitos
        JPanel pCenter = new JPanel(new BorderLayout());
        JLabel bg = new JLabel();
        bg.setHorizontalAlignment(SwingConstants.CENTER);
        setImage(bg, "/resources/fundo_arena.jpg");
        pCenter.add(bg, BorderLayout.CENTER);

        center.add(p1);
        center.add(pCenter);
        center.add(p2);

        content.add(center, BorderLayout.CENTER);

        // Right / bottom: controles e log
        JPanel bottom = new JPanel(new BorderLayout(6, 6));
        taLog = new JTextArea(8, 30);
        taLog.setEditable(false);
        taLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(taLog);
        bottom.add(sp, BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        btnAtacar1 = new JButton("Atacar (Maximus)");
        btnAtacar2 = new JButton("Atacar (Cassius)");
        btnReset = new JButton("Reiniciar");
        btnAuto = new JButton("Auto: OFF");

        controls.add(btnAtacar1);
        controls.add(btnAtacar2);
        controls.add(btnAuto);
        controls.add(btnReset);
        bottom.add(controls, BorderLayout.SOUTH);

        content.add(bottom, BorderLayout.SOUTH);

        // Listeners
        btnAtacar1.addActionListener(e -> executarAtaque(g1, g2));
        btnAtacar2.addActionListener(e -> executarAtaque(g2, g1));
        btnReset.addActionListener(e -> resetarJogo());
        btnAuto.addActionListener(e -> toggleAutoMode());

        // Timer para auto-run e atualizações visuais (animação leve)
        animaTimer = new Timer(800, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarStatus();
                if (autoMode && g1.isVivo() && g2.isVivo()) {
                    // turno aleatório
                    if (Math.random() < 0.5)
                        executarAtaque(g1, g2);
                    else
                        executarAtaque(g2, g1);
                }
            }
        });
        animaTimer.start();

        // initial log
        log("=== BEM-VINDO À ARENA GLADIAMO ===");
        log(g1.getNome() + " VS " + g2.getNome());
        atualizarStatus();

        frame.setVisible(true);
    }

    private void setImage(JLabel label, String path) {
        // tenta carregar do resources; se falhar, usa texto
        try {
            // tenta carregar como recurso relativo ao jar/pasta classes
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(img));
            label.setText("");
        } catch (Exception ex) {
            // fallback: label com texto
            label.setIcon(null);
            label.setText("<html><center>Imagem<br>não encontrada<br>" + path + "</center></html>");
            label.setForeground(Color.WHITE);
        }
    }

    private void executarAtaque(Gladiador atacante, Gladiador alvo) {
        if (!atacante.isVivo() || !alvo.isVivo()) {
            log("Um dos gladiadores já está morto. Reinicie para nova luta.");
            return;
        }

        // animação simples: desabilitar botões
        setBotoesAtivos(false);

        int resultadoAttackResult = atacante.atacar();
        boolean podeAcertar = Math.random() > 0.05; // 5% chance de errar
        if (!podeAcertar) {
            log(atacante.getNome() + " errou o golpe!");
            arena.ajustarHumor(-5);
        } else {
            int danoAntes = resultadoAttackResult;
            int danoAplicado = Math.max(0, danoAntes - (alvo.temArmadura() ? 3 : 0)
                    - (danoAntes * alvo.getDefesa() / 100));
            log(atacante.getNome() + " atacou com " + atacante.getArma().getNome()
                    + " causando ~" + danoAplicado + " de dano em " + alvo.getNome());
            arena.ajustarHumor(3);
        }

        // checar morte
        if (!alvo.isVivo()) {
            log("+++ " + alvo.getNome() + " caiu! " + atacante.getNome() + " vence! +++");
            arena.ajustarHumor(15);
            setBotoesAtivos(false);
        } else {
            // reabilitar botões depois de um pequeno delay (simula animação)
            Timer t = new Timer(600, (ev) -> {
                setBotoesAtivos(true);
                ((Timer) ev.getSource()).stop();
            });
            t.setRepeats(false);
            t.start();
        }

        atualizarStatus();
    }

    private void setBotoesAtivos(boolean ativo) {
        btnAtacar1.setEnabled(ativo && g1.isVivo() && g2.isVivo() && !autoMode);
        btnAtacar2.setEnabled(ativo && g2.isVivo() && g1.isVivo() && !autoMode);
    }

    private void atualizarStatus() {
        pbG1Vida.setMaximum(g1.getVidaMax());
        pbG1Vida.setValue(g1.getVidaAtual());
        pbG1Vida.setString(g1.getNome() + " - " + g1.getVidaAtual() + "/" + g1.getVidaMax());

        pbG2Vida.setMaximum(g2.getVidaMax());
        pbG2Vida.setValue(g2.getVidaAtual());
        pbG2Vida.setString(g2.getNome() + " - " + g2.getVidaAtual() + "/" + g2.getVidaMax());

        lblHumor.setText("Humor da Arena: " + arena.avaliarHumor() + " (" + arena.getHumor() + ")");

        // visual feedback dependendo do humor
        Color bgColor;
        int h = arena.getHumor();
        if (h > 50)
            bgColor = new Color(255, 240, 200);
        else if (h > 10)
            bgColor = new Color(255, 255, 230);
        else if (h > -10)
            bgColor = Color.GRAY;
        else if (h > -50)
            bgColor = new Color(60, 60, 60);
        else
            bgColor = new Color(40, 20, 20);
        frame.getContentPane().setBackground(bgColor);

        // desabilitar botões se alguém morreu
        if (!g1.isVivo() || !g2.isVivo()) {
            setBotoesAtivos(false);
        } else if (!autoMode) {
            setBotoesAtivos(true);
        }
    }

    private void resetarJogo() {
        g1.reset();
        g2.reset();
        arena.resetHumor();
        log("--- Jogo reiniciado ---");
        atualizarStatus();
        autoMode = false;
        btnAuto.setText("Auto: OFF");
        setBotoesAtivos(true);
    }

    private void toggleAutoMode() {
        autoMode = !autoMode;
        btnAuto.setText(autoMode ? "Auto: ON" : "Auto: OFF");
        if (autoMode) {
            setBotoesAtivos(false);
            log("Modo automático ativado.");
        } else {
            setBotoesAtivos(true);
            log("Modo automático desativado.");
        }
    }

    private void log(String texto) {
        taLog.append(texto + "\n");
        taLog.setCaretPosition(taLog.getDocument().getLength());
    }

    public static void main(String[] args) {
        // rodar na EDT
        SwingUtilities.invokeLater(() -> {
            new GladiAmoGUI();
        });
    }
}
