package controller.strategy;

import cipher.FileHelper;
import cipher.symmetric.modern.*;
import enums.InputType;
import enums.SymmetricAlgorithm;
import view.bottom.BottomPanel;
import view.top.SymmetricCard;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class ModernSymmetricControllerStrategy implements CipherControllerStrategy {
    private ModernSymmetricCipher modernSymmetricCipher;
    private SymmetricCard view;
    private InputType type = InputType.TEXT_TYPE;

    public ModernSymmetricControllerStrategy() {
        this.modernSymmetricCipher = new ModernSymmetricCipher(SymmetricAlgorithm.AES_CBC_PKCS5PADDING);
    }

    public void setView(SymmetricCard view) {
        this.view = view;
    }

    public void setModernSymmetric(String algorithm) {
        SymmetricAlgorithm sa = this.modernSymmetricCipher.findSymmetricAlgorithm(algorithm);
        if (sa == null)
            return;
        this.modernSymmetricCipher = new ModernSymmetricCipher(sa);
        if (algorithm.contains("GCM"))
            this.modernSymmetricCipher = new GCMModeSymmetricCipher(sa);
        if ("ChaCha20".equals(algorithm))
            this.modernSymmetricCipher = new ChaCha20SymmetricCipher(sa);
        if (sa.getParameterSpecSize() == 0)
            this.modernSymmetricCipher = new NoIVSymmetricCipher(sa);
        if (algorithm.contains("Camellia") || algorithm.contains("CAST5") || algorithm.contains("KASUMI"))
            this.modernSymmetricCipher = new BcProviderSymmetricCipher(sa);
    }

    @Override
    public void encrypt(String data) throws Exception{
        view.saveConfig(modernSymmetricCipher);
        switch (type) {
            case TEXT_TYPE: {
                BottomPanel.updateResult(this.modernSymmetricCipher.encryptText(data));
                break;
            }
            case FILE_TYPE: {
                File file = new File(data);
                if (!file.isFile()) throw new Exception("Đường dẫn không phải là tệp");
                this.modernSymmetricCipher.encryptFile(data, file.getParent() + "/encrypt" + FileHelper.getExtension(file));
                BottomPanel.updateResult("Đã mã hóa thành công");
                break;
            }
        }
    }

    @Override
    public void decrypt(String data) throws Exception{
        view.saveConfig(modernSymmetricCipher);
        switch (type) {
            case TEXT_TYPE: {
                BottomPanel.updateResult(this.modernSymmetricCipher.decryptText(data));
                break;
            }
            case FILE_TYPE: {
                File file = new File(data);
                if (!file.isFile()) throw new Exception("Đường dẫn không phải là tệp");
                this.modernSymmetricCipher.decryptFile(data, file.getParent() + "/decrypt" + FileHelper.getExtension(file));
                BottomPanel.updateResult("Đã giải mã thành công");
                break;
            }
        }
    }

    @Override
    public void inputTypeChanged(InputType type) {
        this.type = type;
    }

    public String[] getAlgorithms() {
        return this.modernSymmetricCipher.getAlgorithms();
    }

    public String genKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        return this.modernSymmetricCipher.genKey();
    }

    public String genIV() {
        return this.modernSymmetricCipher.genIV();
    }

    public String[] findKeySizes() {
        return this.modernSymmetricCipher.findKeySizes();
    }

    public void exportKey(File des) throws IOException {
        String result ="Đã xuất khóa cho bạn";
        if (!this.modernSymmetricCipher.exportKey(des))
            result = "Không thể xuất khóa cho bạn";
        BottomPanel.updateResult(result);
    }

    public String importKey(File src) throws IOException {
        return this.modernSymmetricCipher.importKey(src);
    }

    public void exportIV(File des) throws IOException {
        String result = "Đã xuất IV cho bạn";
        if (!this.modernSymmetricCipher.exportIV(des))
            result = "Không thể xuất IV cho bạn";
        BottomPanel.updateResult(result);
    }

    public String importIV(File src) throws IOException {
        return this.modernSymmetricCipher.importIV(src);
    }

    public ModernSymmetricCipher getCipher() {
        return this.modernSymmetricCipher;
    }

    public void updateView() {
        this.view.update(this.modernSymmetricCipher);
    }
}
