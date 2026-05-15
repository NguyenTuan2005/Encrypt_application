package cipher.symmetric.modern;

import utils.FileHelper;
import enums.SymmetricAlgorithm;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
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
    public String getIV() {
        return Base64.getEncoder().encodeToString(((GCMParameterSpec) this.symmetric.getIv()).getIV());
    }

    @Override
    public void encryptCopy(Cipher cipher, DataOutputStream out) throws IOException, IllegalBlockSizeException, BadPaddingException {
        byte[] encrypted = cipher.doFinal(String.valueOf(this.symmetric.getKeySize()).getBytes(StandardCharsets.UTF_8));
        out.writeInt(encrypted.length);
        out.write(encrypted);
        encrypted = cipher.doFinal(this.symmetric.getTransformation().getBytes(StandardCharsets.UTF_8));
        out.writeInt(encrypted.length);
        out.write(encrypted);
        encrypted = cipher.doFinal(this.symmetric.getSecretKey().getEncoded());
        out.writeInt(encrypted.length);
        out.write(encrypted);
        encrypted = cipher.doFinal(((GCMParameterSpec) this.symmetric.getIv()).getIV());
        out.writeInt(encrypted.length);
        out.write(encrypted);
    }

    @Override
    public void decryptCopy(Cipher cipher, DataInputStream in) throws IOException, IllegalBlockSizeException, BadPaddingException {
        byte[] rawKeySize = in.readNBytes(in.readInt());
        this.symmetric.setKeySize(Integer.parseInt(new String(cipher.doFinal(rawKeySize), StandardCharsets.UTF_8)));
        byte[] transformation = in.readNBytes(in.readInt());
        SymmetricAlgorithm sa = findSymmetricAlgorithm(new String(cipher.doFinal(transformation), StandardCharsets.UTF_8));
        this.symmetric.setSymmetricAlgorithm(sa);
        byte[] secretKey = in.readNBytes(in.readInt());
        this.symmetric.setSecretKey(new SecretKeySpec(cipher.doFinal(secretKey), this.symmetric.getAlgorithmName()));
        byte[] parameterSpec = in.readNBytes(in.readInt());
        this.symmetric.setAlgorithmParameterSpec(new GCMParameterSpec(128, cipher.doFinal(parameterSpec)));
    }
}
