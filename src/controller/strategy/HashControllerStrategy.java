package controller.strategy;

import cipher.hash.HashCipher;
import enums.HashAlgorithm;
import enums.InputType;
import view.bottom.BottomPanel;

import java.io.File;

public class HashControllerStrategy implements CipherControllerStrategy {
    private HashCipher hashCipher;
    private InputType type = InputType.TEXT_TYPE;

    public HashControllerStrategy() {
        this.hashCipher = new HashCipher(HashAlgorithm.SHA1);
    }

    @Override
    public void encrypt(String data) throws Exception {
    }

    @Override
    public void decrypt(String data) throws Exception {
    }

    @Override
    public void inputTypeChanged(InputType type) {
        this.type = type;
    }

    public void genHash(String data) throws Exception {
        switch (type) {
            case TEXT_TYPE: {
                BottomPanel.updateResult(this.hashCipher.hashText(data));
                break;
            }
            case FILE_TYPE: {
                File file = new File(data);
                if (!file.isFile()) throw new Exception("Đường dẫn không phải là tệp");
                BottomPanel.updateResult(this.hashCipher.hashFile(file));
                break;
            }
        }
    }

    public String[] getAlgorithms() {
        return this.hashCipher.getAlgorithms();
    }

    public void setHashCipher(String algorithm) {
        HashAlgorithm ha = this.hashCipher.findHashAlgorithm(algorithm);
        if (ha == null) return;
        this.hashCipher = new HashCipher(ha);
    }
}
