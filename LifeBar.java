
package ui;

import javax.swing.*;
import java.awt.*;

public class LifeBar extends JPanel {

    private int max;
    private int atual;
    private int animAtual;

    public LifeBar(int max) {
        this.max = max;
        this.atual = max;
        this.animAtual = max;

        setPreferredSize(new Dimension(180, 28));
        setOpaque(false);

        // Timer da animação suave
        Timer timer = new Timer(25, e -> {
            if (animAtual > atual) {
                animAtual -= 1;
                repaint();
            }
        });
        timer.start();
    }

    public void animarPara(int novoValor) {
        this.atual = novoValor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Anti-serrilho
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // --- Fundo medieval (couro escuro) ---
        g2.setColor(new Color(60, 40, 20));
        g2.fillRoundRect(0, 0, width, height, 12, 12);

        // --- Borda metálica ---
        g2.setColor(new Color(180, 150, 80));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(0, 0, width, height, 12, 12);

        // --- Barra vermelha (vida) ---
        float perc = (float) animAtual / max;
        int barWidth = (int) (perc * (width - 4));

        // degradê vermelho medieval
        GradientPaint grad = new GradientPaint(
                0, 0, new Color(150, 20, 20),
                barWidth, 0, new Color(255, 60, 60)
        );

        g2.setPaint(grad);
        g2.fillRoundRect(2, 2, barWidth, height - 4, 10, 10);

        // texto
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 16));
        String texto = animAtual + " / " + max;
        g2.drawString(texto, (width / 2) - (texto.length() * 3), height - 7);
    }
}
