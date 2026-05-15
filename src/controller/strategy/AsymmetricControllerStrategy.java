package controller.strategy;

import cipher.FileHelper;
import cipher.asymmetric.AsymmetricCipher;
import controller.EncryptionController;
import enums.AsymmetricAlgorithm;
import enums.InputType;
import view.bottom.BottomPanel;
import view.top.AsymmetricCard;
import view.top.SymmetricCard;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import static enums.InputType.TEXT_TYPE;

public class AsymmetricControllerStrategy implements CipherControllerStrategy {
    private AsymmetricCipher asymmetricCipher;
    private AsymmetricCard asymmetricCard;
    private SymmetricCard symmetricCard;
    private InputType inputType = TEXT_TYPE;

    public AsymmetricControllerStrategy() {
        this.asymmetricCipher = new AsymmetricCipher(AsymmetricAlgorithm.RSA_ECB_PKCS1PADDING);
    }

    public void setAsymmetricCard(AsymmetricCard asymmetricCard) {
        this.asymmetricCard = asymmetricCard;
    }

    public void setSymmetricCard(SymmetricCard symmetricCard) {
        this.symmetricCard = symmetricCard;
    }

    @Override
    public void encrypt(String data) throws Exception {
        asymmetricCard.saveConfig(asymmetricCipher);
        switch (inputType) {
            case TEXT_TYPE: {
                BottomPanel.updateResult(this.asymmetricCipher.encryptText(data));
                break;
            }
            case FILE_TYPE: {
                File file = new File(data);
                if (!file.isFile()) throw new Exception("Đường dẫn không phải là tệp");
                ModernSymmetricControllerStrategy modernController =
                        (ModernSymmetricControllerStrategy) EncryptionController.getInstance().get("Modern");
                this.symmetricCard.saveConfig(modernController.getCipher());

                this.asymmetricCipher.encryptFile(modernController.getCipher(), data, file.getParent() + "/ecrypt" + FileHelper.getExtension(file));

                modernController.updateView();
                BottomPanel.updateResult("Đã mã hóa thành công");
                break;
            }
        }
    }

    @Override
    public void decrypt(String data) throws Exception {
        asymmetricCard.saveConfig(asymmetricCipher);
        switch (inputType) {
            case TEXT_TYPE: {
                BottomPanel.updateResult(this.asymmetricCipher.decryptText(data));
                break;
            }
            case FILE_TYPE: {
                File file = new File(data);
                if (!file.isFile()) throw new Exception("Đường dẫn không phải là tệp");
                ModernSymmetricControllerStrategy modernController =
                        (ModernSymmetricControllerStrategy) EncryptionController.getInstance().get("Modern");
                this.symmetricCard.saveConfig(modernController.getCipher());

                this.asymmetricCipher.decryptFile(modernController.getCipher(), data, file.getParent() + "/decrypt" + FileHelper.getExtension(file));

                modernController.updateView();
                BottomPanel.updateResult("Đã giải mã thành công");
                break;
            }
        }
    }

    @Override
    public void inputTypeChanged(InputType type) {
        this.inputType = type;
    }

    public String[] getTransformations() {
        return this.asymmetricCipher.getTransformations();
    }

    public String[] findKeySizes() {
        return this.asymmetricCipher.findKeySizes();
    }

    public void setAsymmetricCipher(String algorithm) {
        AsymmetricAlgorithm aa = this.asymmetricCipher.findAsymmetricAlgorithm(algorithm);
        if (aa == null) return;
        this.asymmetricCipher = new AsymmetricCipher(aa);
    }

    public String[] genKeyPair() throws NoSuchAlgorithmException {
        return this.asymmetricCipher.genKey();
    }
}
