
// SoundUtil.java
import javax.sound.sampled.*;

public class SoundUtil {
    // Gera um curto "beep" / impacto — sem arquivos externos
    public static void playTone(int hz, int msecs, double vol) {
        final float SAMPLE_RATE = 44100;
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
        try (SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < msecs * (float)SAMPLE_RATE / 1000; i++) {
                double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
                buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
        } catch (LineUnavailableException e) {
            // falha em som — ignore silenciosamente
        }
    }

    public static void impact() {
        // rápido impacto composto
        new Thread(() -> {
            playTone(400, 30, 0.8);
            try { Thread.sleep(30); } catch (InterruptedException ignored) {}
            playTone(600, 50, 0.6);
        }).start();
    }

    public static void cheer() {
        new Thread(() -> {
            playTone(800, 40, 0.5);
            try { Thread.sleep(40); } catch (InterruptedException ignored) {}
            playTone(1000, 80, 0.4);
        }).start();
    }

    public static void miss() {
        new Thread(() -> playTone(200, 60, 0.4)).start();
    }
    
}
