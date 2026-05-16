package controller.strategy;

import cipher.symmetric.tradition.ShiftSymmetricCipher;
import cipher.symmetric.tradition.TraditionSymmetricCipher;
import enums.InputType;
import view.bottom.BottomPanel;
import view.top.TraditionalSymmetricCard;

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
                break;
            case "Affine":
                break;
            case "Vigenere":
                break;
            case "Hill":
                break;
            case "Mã hóa hoán vị":
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
}
