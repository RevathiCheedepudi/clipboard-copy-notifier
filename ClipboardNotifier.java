import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class ClipboardNotifier {
    public static void main(String[] args) throws Exception {
        if (!SystemTray.isSupported()) {
            System.out.println("System tray not supported!");
            return;
        }

        // Create tray icon
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png"); // use a 16x16 png
        TrayIcon trayIcon = new TrayIcon(image, "Clipboard Notifier");
        trayIcon.setImageAutoSize(true);
        tray.add(trayIcon);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String lastText = "";

        while (true) {
            try {
                String data = (String) clipboard.getData(DataFlavor.stringFlavor);

                if (data != null && !data.equals(lastText)) {
                    lastText = data;
                    trayIcon.displayMessage("Copied!", data, TrayIcon.MessageType.INFO);
                    Toolkit.getDefaultToolkit().beep(); // sound feedback
                }
            } catch (UnsupportedFlavorException | IOException e) {
                // Handle non-text content gracefully
            }
            Thread.sleep(500); // check every 0.5 sec
        }
    }
}
