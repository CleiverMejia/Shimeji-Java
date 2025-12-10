package view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Window;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main extends JFrame {

    private static final Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    public static final int SCREEN_WIDTH = screenSize.width;
    public static final int SCREEN_HEIGHT = screenSize.height;

    public void run() throws AWTException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        JPanel containerPanel = new JPanel();

        containerPanel.setLayout(null);
        containerPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setOpaque(false);

        containerPanel.add(new Ski());
        containerPanel.add(new Ski());
        containerPanel.add(new Ski());

        this.add(containerPanel);

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
            Image icon = new ImageIcon(Main.class.getResource("/assets/Ski.png")).getImage();

            PopupMenu menu = new PopupMenu();
            MenuItem abrir = new MenuItem("Llamar");
            MenuItem salir = new MenuItem("Cerrar");

            abrir.addActionListener(e -> this.setVisible(true));
            salir.addActionListener(e -> System.exit(0));

            menu.add(abrir);
            menu.add(salir);

            TrayIcon trayIcon = new TrayIcon(icon, "Ski Shimeji", menu);
            trayIcon.setImageAutoSize(true);

            tray.add(trayIcon);
        }
    }
}
