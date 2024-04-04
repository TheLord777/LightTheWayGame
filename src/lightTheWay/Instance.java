package lightTheWay;

import processing.core.PApplet;

/**
 * Singleton class for accessing the app.
 */
public class Instance {

    private static boolean lock = false;
    private static PApplet app;

    public static void setApp(PApplet app) {
        if (!lock) Instance.app = app;
        lock = true;
    }

    public static PApplet getApp() {
        return app;
    }
}
