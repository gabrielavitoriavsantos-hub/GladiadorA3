package ui;

import javax.sound.sampled.*;
import java.net.URL;

public class Som {

    public static Clip tocar(String nomeArquivo) {
        Clip clip = null;  // <-- fora do try (IMPORTANTE)
        try {
            URL url = Som.class.getResource("/assets/" + nomeArquivo);
            if (url == null) {
                System.out.println("Som não encontrado: " + nomeArquivo);
                return null;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();  // <-- também fora do try NÃO pode estar
            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            System.out.println("Erro ao tocar som: " + nomeArquivo);
        }
        return clip;  // <-- retorno FORA do try/catch
    }

    public static Clip tocarLoop(String nomeArquivo) {
        Clip clip = null;
        try {
            URL url = Som.class.getResource("/assets/" + nomeArquivo);
            if (url == null) {
                System.out.println("Som não encontrado: " + nomeArquivo);
                return null;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            System.out.println("Erro ao tocar loop: " + nomeArquivo);
        }
        return clip;
    }

    public static void parar(Clip c) {
        if (c != null && c.isRunning()) {
            try { c.stop(); } catch (Exception ignored) {}
        }
    }

    public static void tocarVitoria() {
        tocar("vitoria.wav");
    }
}

