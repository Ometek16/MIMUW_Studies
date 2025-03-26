package lab02.assignments;

import java.io.IOException;
import java.time.Clock;

public class FileDownloader {
    private static volatile int progress = 0;
    private static final int PROGRESS_MAX = 100;

    private static void doDownload() throws InterruptedException {
        while (progress < PROGRESS_MAX) {
            Thread.sleep(50); // Simulate download.
            progress++;
        }
    }

    private static void refreshUI() {
        while (true) {
            // This clears the console.
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Time: " + Clock.systemDefaultZone().instant().toString());
            System.out.println("Progress: " + progress + " / " + PROGRESS_MAX);

            if (progress == 100) {
                System.out.println("Download complete.");
                break;
            } else if (progress == 0) {
                System.out.println("Press enter to start downloading.");
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Thread refresh = new Thread(() -> {refreshUI();});
            refresh.start();

            while (refresh.isAlive()) {
                // Check if user entered any input.
                if (System.in.available() == 0) {
                    Thread.sleep(100);
                    continue;
                }
                int c = System.in.read();
                if (c == '\n') {
                    doDownload();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
