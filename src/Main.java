import controller.EncryptionController;
import controller.strategy.AsymmetricControllerStrategy;
import controller.strategy.HashControllerStrategy;
import controller.strategy.ModernSymmetricControllerStrategy;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import view.EncryptApplication;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        EncryptionController controller = EncryptionController.getInstance();
        controller.put("Modern", new ModernSymmetricControllerStrategy());
        controller.put("Asymmetric", new AsymmetricControllerStrategy());
        controller.put("Hash", new HashControllerStrategy());

        EncryptApplication encryptApplication = new EncryptApplication();
        encryptApplication.showApp();
    }
}
