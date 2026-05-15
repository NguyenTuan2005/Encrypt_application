import controller.EncryptionController;
import controller.strategy.AsymmetricControllerStrategy;
import controller.strategy.ModernSymmetricControllerStrategy;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import view.EncryptApplication;

import java.security.Security;

public class Main {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        EncryptionController controller = EncryptionController.getInstance();
        controller.put("Modern", new ModernSymmetricControllerStrategy());
        controller.put("Asymmetric", new AsymmetricControllerStrategy());

        EncryptApplication encryptApplication = new EncryptApplication();
        encryptApplication.showApp();
    }
}
