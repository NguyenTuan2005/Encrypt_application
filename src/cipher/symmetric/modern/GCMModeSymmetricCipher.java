package cipher.symmetric.modern;

import cipher.FileHelper;
import enums.SymmetricAlgorithm;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GCMModeSymmetricCipher extends ModernSymmetricCipher {
    public GCMModeSymmetricCipher(SymmetricAlgorithm algorithm) {
        super(algorithm);
    }

    @Override
    public String genIV() {
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, new byte[symmetric.getParameterSpecSize()]);
        symmetric.setAlgorithmParameterSpec(gcmParameterSpec);
        return Base64.getEncoder().encodeToString(gcmParameterSpec.getIV());
    }

    @Override
    public boolean exportIV(File des) throws IOException {
        if (this.symmetric.getIv() == null) return false;
        FileHelper.exportIV(((GCMParameterSpec) this.symmetric.getIv()).getIV(), des);
        return true;
    }

    @Override
    public String importIV(File src) throws IOException {
        byte[] iv = FileHelper.importIV(src);
        this.symmetric.setAlgorithmParameterSpec(new GCMParameterSpec(128, iv));
        return Base64.getEncoder().encodeToString(((GCMParameterSpec) this.symmetric.getIv()).getIV());
    }

    @Override
    public void encryptCopy(Cipher cipher, DataOutputStream out) throws IOException, IllegalBlockSizeException, BadPaddingException {
        out.write(cipher.doFinal(String.valueOf(this.symmetric.getKeySize()).getBytes(StandardCharsets.UTF_8)));
        byte[] transformation = this.symmetric.getTransformation().getBytes(StandardCharsets.UTF_8);
        out.write(cipher.doFinal(transformation));
        out.write(cipher.doFinal(this.symmetric.getSecretKey().getEncoded()));
        out.write(cipher.doFinal(((GCMParameterSpec) this.symmetric.getIv()).getIV()));
    }

    @Override
    public String getIV() {
        return Base64.getEncoder().encodeToString(((GCMParameterSpec) this.symmetric.getIv()).getIV());
    }
}
