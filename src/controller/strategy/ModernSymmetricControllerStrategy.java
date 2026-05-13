package controller.strategy;

import cipher.symmetric.modern.ModernSymmetric;
import enums.InputType;
import enums.SymmetricAlgorithm;
import view.bottom.BottomPanel;
import view.top.SymmetricCard;

import java.io.File;
import java.security.NoSuchAlgorithmException;

public class ModernSymmetricControllerStrategy implements CipherControllerStrategy {
    private ModernSymmetric modernSymmetric;
    private SymmetricCard view;
    private InputType type = InputType.TEXT_TYPE;

    public ModernSymmetricControllerStrategy() {
        this.modernSymmetric = new ModernSymmetric(SymmetricAlgorithm.AES_CBC_NOPADDING);
    }

    public void setView(SymmetricCard view) {
        this.view = view;
    }

    @Override
    public void encrypt(String data) throws Exception{
        view.saveConfig(modernSymmetric);
        switch (type) {
            case TEXT_TYPE: {
                BottomPanel.updateResult(this.modernSymmetric.encryptText(data));
                break;
            }
            case FILE_TYPE: {
                File file = new File(data);
                if (!file.isFile()) throw new Exception("Đường dẫn không phải là tệp");
                this.modernSymmetric.encryptFile(data, file.getParent() + "/encrypt.txt");
                break;
            }
        }
    }

    @Override
    public void decrypt(String data) {
        try {
            switch (type) {
                case TEXT_TYPE: {
                    BottomPanel.updateResult(this.modernSymmetric.decryptText(data));
                    break;
                }
                case FILE_TYPE: {
                    File file = new File(data);
                    if (!file.isFile()) throw new Exception("Đường dẫn không phải là tệp");
                    this.modernSymmetric.decryptFile(data, file.getParent() + "/decrypt.txt");
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void inputTypeChanged(InputType type) {
        this.type = type;
    }

    public String[] getAlgorithms() {
        return this.modernSymmetric.getAlgorithms();
    }

    public String[] getKeySizes() {
        return this.modernSymmetric.getKeySizes();
    }

    public String genKey() throws NoSuchAlgorithmException {
        return this.modernSymmetric.genKey();
    }

    public String genIV() {
        return this.modernSymmetric.genIV();
    }

    public String[] findKeySizes(String algorithm) {
        return this.modernSymmetric.findKeySizes(algorithm);
    }
}
