package controller.strategy;

import cipher.symmetric.tradition.*;
import enums.InputType;
import view.bottom.BottomPanel;
import view.top.TraditionalSymmetricCard;

import java.io.File;
import java.io.IOException;

public class TraditionSymmetricControllerStrategy implements CipherControllerStrategy {
    private TraditionSymmetricCipher traditionSymmetricCipher;
    private TraditionalSymmetricCard view;

    public TraditionSymmetricControllerStrategy() {
        this.traditionSymmetricCipher = new ShiftSymmetricCipher(3);
    }

    public void setTraditionSymmetricCipher(String cipher) {
        switch (cipher) {
            case "Mã hóa dịch chuyển":
                this.traditionSymmetricCipher = new ShiftSymmetricCipher(3);
                break;
            case "Mã hóa thay thế":
                this.traditionSymmetricCipher = new SubstitutionSymmetricCipher();
                break;
            case "Affine":
                break;
            case "Vigenere":
                this.traditionSymmetricCipher = new VigenereSymmetricCipher();
                break;
            case "Hill":
                break;
            case "Mã hóa hoán vị":
                this.traditionSymmetricCipher = new PermutationSymmetricCipher();
                break;
        }
    }

    @Override
    public void encrypt(String data) throws Exception {
        view.updateConfig(this.traditionSymmetricCipher);
        BottomPanel.updateResult(traditionSymmetricCipher.encryptText(data.trim()));
    }

    @Override
    public void decrypt(String data) throws Exception {
        view.updateConfig(this.traditionSymmetricCipher);
        BottomPanel.updateResult(traditionSymmetricCipher.decryptText(data.trim()));
    }

    @Override
    public void inputTypeChanged(InputType type) {
    }

    public void setView(TraditionalSymmetricCard traditionalSymmetricCard) {
        this.view = traditionalSymmetricCard;
    }

    public String genKey() {
        return this.traditionSymmetricCipher.genKey();
    }

    public String exportKey(File des) throws IOException {
        return this.traditionSymmetricCipher.exportKey(des);
    }

    public String importKey(File src) throws IOException {
        return this.traditionSymmetricCipher.importKey(src);
    }

    public String filterValid(String text) {
        return this.traditionSymmetricCipher.filterValid(text);
    }
}
