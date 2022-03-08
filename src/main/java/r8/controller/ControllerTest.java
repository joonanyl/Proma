package r8.controller;

public class ControllerTest {
    public static void main(String[] args) {
        Controller controller = new Controller();

        // controller.createAccount("Test", "User", "test@email.com", "password");
        /*controller.authenticateLogin("test@email.com", "password");

        controller.updateAccount("Testing", "Update", "test@update.com", "update");
        controller.logout();

         */
        controller.authenticateLogin("test@update.com", "update");
        // NullPointerException
        System.out.println(controller.getAccount(1));
    }
}
