package view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Window;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main extends JFrame {

    private static final Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    public static final int SCREEN_WIDTH = screenSize.width;
    public static final int SCREEN_HEIGHT = screenSize.height;

    public void run() throws AWTException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
        JPanel sandbox = new Sandbox();
        Config configPanel = new Config();

        sandbox.add(configPanel);

        this.add(sandbox);

        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setType(Window.Type.UTILITY);
        this.setVisible(true);

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        if (SystemTray.isSupported()) {
            SystemTray tray;
            tray = SystemTray.getSystemTray();
            Image icon = new ImageIcon(Main.class.getResource("/assets/Ski_icon.png")).getImage();

            PopupMenu menu = new PopupMenu();
            MenuItem call = new MenuItem("Llamar");
            MenuItem config = new MenuItem("Configuracion");
            MenuItem hammer = new MenuItem("Martillo");
            MenuItem close = new MenuItem("Cerrar");

            call.addActionListener(e -> this.setVisible(true));
            close.addActionListener(e -> System.exit(0));
            config.addActionListener(e -> configPanel.setVisible(true));

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image img = new ImageIcon(Main.class.getResource("/assets/Hammer1.png")).getImage();

            Point hotspot = new Point(19, 20);
            Cursor cursor = toolkit.createCustomCursor(img, hotspot, "MiCursor");
            hammer.addActionListener(e -> this.setCursor(cursor));

            menu.add(call);
            menu.add(config);
            menu.add(hammer);
            menu.add(close);

            TrayIcon trayIcon = new TrayIcon(icon, "Ski Shimeji", menu);
            trayIcon.setImageAutoSize(true);

            tray.add(trayIcon);
        }
    }
}
