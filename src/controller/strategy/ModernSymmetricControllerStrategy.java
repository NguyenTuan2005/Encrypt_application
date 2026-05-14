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
    private ModernSymmetric modernSymmetric;
    private SymmetricCard view;
    private InputType type = InputType.TEXT_TYPE;

    public ModernSymmetricControllerStrategy() {
        this.modernSymmetric = new ModernSymmetric(SymmetricAlgorithm.AES_CBC_NOPADDING);
    }

    public void setView(SymmetricCard view) {
        this.view = view;
    }

    public void setModernSymmetric(String algorithm) {
        SymmetricAlgorithm sa = this.modernSymmetric.findSymmetricAlgorithm(algorithm);
        if (sa == null)
            return;
        this.modernSymmetric = new ModernSymmetric(sa);
        if (algorithm.contains("GCM"))
            this.modernSymmetric = new GCMModeSymmetric(sa);
        if ("ChaCha20".equals(algorithm))
            this.modernSymmetric = new ChaCha20Symmetric(sa);
        if (sa.getParameterSpecSize() == 0)
            this.modernSymmetric = new NoIVSymmetric(sa);
        if (algorithm.contains("Camellia") || algorithm.contains("CAST5") || algorithm.contains("KASUMI"))
            this.modernSymmetric = new BcProviderSymmetric(sa);
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
                this.modernSymmetric.encryptFile(data, file.getParent() + "/encrypt" + FileHelper.getExtension(file));
                BottomPanel.updateResult("Đã mã hóa thành công");
                break;
            }
        }
    }

    @Override
    public void decrypt(String data) throws Exception{
        switch (type) {
            case TEXT_TYPE: {
                BottomPanel.updateResult(this.modernSymmetric.decryptText(data));
                break;
            }
            case FILE_TYPE: {
                File file = new File(data);
                if (!file.isFile()) throw new Exception("Đường dẫn không phải là tệp");
                this.modernSymmetric.decryptFile(data, file.getParent() + "/decrypt" + FileHelper.getExtension(file));
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
        return this.modernSymmetric.getAlgorithms();
    }

    public String[] getKeySizes() {
        return this.modernSymmetric.getKeySizes();
    }

    public String genKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        return this.modernSymmetric.genKey();
    }

    public String genIV() {
        return this.modernSymmetric.genIV();
    }

    public String[] findKeySizes() {
        return this.modernSymmetric.findKeySizes();
    }

    public void exportKey(File des) throws IOException {
        String result ="Đã xuất khóa cho bạn";
        if (!this.modernSymmetric.exportKey(des))
            result = "Không thể xuất khóa cho bạn";
        BottomPanel.updateResult(result);
    }

    public String importKey(File src) throws IOException {
        return this.modernSymmetric.importKey(src);
    }

    public void exportIV(File des) throws IOException {
        String result = "Đã xuất IV cho bạn";
        if (!this.modernSymmetric.exportIV(des))
            result = "Không thể xuất IV cho bạn";
        BottomPanel.updateResult(result);
    }

    public String importIV(File src) throws IOException {
        return this.modernSymmetric.importIV(src);
    }
}
