import controller.EncryptionController;
import controller.strategy.ModernSymmetricControllerStrategy;
import view.EncryptApplication;

public class Main {
    public static void main(String[] args) {
        EncryptionController controller = EncryptionController.getInstance();
        controller.put("Modern", new ModernSymmetricControllerStrategy());

        EncryptApplication encryptApplication = new EncryptApplication();
        encryptApplication.showApp();
    }
}
