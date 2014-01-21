import controllers.LoginController;
import helpers.MainScreenSwitcher;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

/**
 * User: Chris and Floris
 * Class: the main program which imports all the made classes utilities
 */
public class Program extends javax.swing.JFrame
{
    private int WIDTH = 1370;
    private int HEIGHT = 768;

    public static void main(String[] args)
    {
        new Program();
    }

    public Program()
    {
        if (isMacOSX()) {
            System.setProperty(
                    "com.apple.mrj.application.apple.menu.about.name",
                    "Reservation Management");
        }

        this.setSize(WIDTH, HEIGHT);
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.setTitle("Reservation Management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setIconImage(new ImageIcon(getClass()
                .getResource("assets/car1.png")).getImage());

        this.add(MainScreenSwitcher.getInstance());

        LoginController loginController = new LoginController();
        MainScreenSwitcher.getInstance().switchScreen(loginController.getView());

        if (isMacOSX()) {
            enableFullScreenMode(this);
            this.setMinimumSize(new Dimension(1260, HEIGHT));
        }
        this.setVisible(true); //show our application
    }

    public static void enableFullScreenMode(Window window) {
        String className = "com.apple.eawt.FullScreenUtilities";
        String methodName = "setWindowCanFullScreen";

        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getMethod(methodName, new Class<?>[] {
                    Window.class, boolean.class });
            method.invoke(null, window, true);
        } catch (Throwable t) {
            System.err.println("Full screen mode is not supported");
            t.printStackTrace();
        }
    }

    private static boolean isMacOSX() {
        return System.getProperty("os.name").indexOf("Mac OS X") >= 0;
    }
}
