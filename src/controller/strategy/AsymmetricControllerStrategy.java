package controller.strategy;

import utils.FileHelper;
import cipher.asymmetric.AsymmetricCipher;
import controller.EncryptionController;
import enums.AsymmetricAlgorithm;
import enums.InputType;
import view.bottom.BottomPanel;
import view.top.AsymmetricCard;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static enums.InputType.TEXT_TYPE;

public class AsymmetricControllerStrategy implements CipherControllerStrategy {
    private AsymmetricCipher asymmetricCipher;
    private AsymmetricCard asymmetricCard;
    private InputType inputType = TEXT_TYPE;

    public AsymmetricControllerStrategy() {
        this.asymmetricCipher = new AsymmetricCipher(AsymmetricAlgorithm.RSA_ECB_PKCS1PADDING);
    }

    public void setAsymmetricCard(AsymmetricCard asymmetricCard) {
        this.asymmetricCard = asymmetricCard;
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
                ModernSymmetricControllerStrategy.getCurrentView().saveConfig(modernController.getCipher());

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
                ModernSymmetricControllerStrategy.getCurrentView().saveConfig(modernController.getCipher());

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

    public void exportPublicKey(File des) throws IOException {
        String result ="Đã xuất khóa công khai cho bạn";
        if (!this.asymmetricCipher.exportPublicKey(des))
            result = "Không thể xuất khóa công khai cho bạn";
        BottomPanel.updateResult(result);
    }

    public String importPublicKey(File src) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return this.asymmetricCipher.importPublicKey(src);
    }

    public void exportPrivateKey(File des) throws IOException {
        String result = "Đã xuất khóa riêng tư cho bạn";
        if (!this.asymmetricCipher.exportPrivateKey(des))
            result = "Không thể xuất khóa riêng tư cho bạn";
        BottomPanel.updateResult(result);
    }

    public String importPrivateKey(File des) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        return this.asymmetricCipher.importPrivateKey(des);
    }
}
