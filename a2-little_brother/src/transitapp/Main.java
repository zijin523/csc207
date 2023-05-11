package transitapp;

import transitcontroller.SystemController;

/**
 * A transit system.
 *
 * @author Hengda Xu, Shuhao Dong, Tianshu Ni, Zijin Liao
 * @version 1.0
 */
public class Main {
    /**
     * Start this transit system.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SystemController system = new SystemController();
        system.openSystemView(args);
    }
}
