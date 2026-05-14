package cipher.symmetric.modern;

import cipher.FileEncryption;
import cipher.FileHelper;
import cipher.TextEncryption;
import enums.SymmetricAlgorithm;
import model.Symmetric;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Base64;

public class ModernSymmetric implements TextEncryption, FileEncryption {
    protected Symmetric symmetric;

    public ModernSymmetric(SymmetricAlgorithm algorithm) {
        this.symmetric = new Symmetric(algorithm);
    }

    public String genKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(symmetric.getAlgorithmName());
        keyGenerator.init(symmetric.getKeySize());
        SecretKey secretKey = keyGenerator.generateKey();
        symmetric.setSecretKey(secretKey);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public String genIV() {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[symmetric.getParameterSpecSize()]);
        symmetric.setAlgorithmParameterSpec(ivParameterSpec);
        return Base64.getEncoder().encodeToString(ivParameterSpec.getIV());
    }

    @Override
    public void encryptFile(String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        FileHelper.copy(src, des, cipher);
    }

    @Override
    public void decryptFile(String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        FileHelper.copy(src, des, cipher);
    }

    @Override
    public String encryptText(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        byte[] data = plainText.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    @Override
    public String decryptText(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        byte[] data = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }

    public String importKey(File src) throws IOException {
        this.symmetric.setSecretKey(FileHelper.importKey(src, this.symmetric.getAlgorithmName()));
        return Base64.getEncoder().encodeToString(this.symmetric.getSecretKey().getEncoded());
    }

    public boolean exportKey(File des) throws IOException {
        if (this.symmetric.getSecretKey() == null) return false;
        FileHelper.exportKey(this.symmetric.getSecretKey().getEncoded(), des);
        return true;
    }

    public String importIV(File src) throws IOException {
        byte[] iv = FileHelper.importIV(src);
        this.symmetric.setAlgorithmParameterSpec(new IvParameterSpec(iv));
        return Base64.getEncoder().encodeToString(((IvParameterSpec) this.symmetric.getIv()).getIV());
    }

    public boolean exportIV(File des) throws IOException {
        if (this.symmetric.getIv() == null) return false;
        FileHelper.exportIV(((IvParameterSpec) this.symmetric.getIv()).getIV(), des);
        return true;
    }

    public String[] getAlgorithms() {
        return this.symmetric.getAlgorithms();
    }

    public String[] getKeySizes() {
        return Arrays.stream(this.symmetric.getKeySizes())
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
    }

    public String[] findKeySizes() {
        return this.symmetric.findKeySizes();
    }

    public void setKeySize(int keySize) {
        this.symmetric.setKeySize(keySize);
    }

    public SymmetricAlgorithm findSymmetricAlgorithm(String algorithms) {
        return this.symmetric.findSymmetricAlgorithm(algorithms);
    }
}
