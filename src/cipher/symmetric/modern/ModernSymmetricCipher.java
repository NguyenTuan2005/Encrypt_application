package cipher.symmetric.modern;

import cipher.FileEncryption;
import utils.FileHelper;
import cipher.TextEncryption;
import enums.SymmetricAlgorithm;
import model.Symmetric;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

public class ModernSymmetricCipher implements TextEncryption, FileEncryption {
    protected Symmetric symmetric;

    public ModernSymmetricCipher(SymmetricAlgorithm algorithm) {
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

    public void encryptFile(String src, DataOutputStream out) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, IOException, BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        FileHelper.copy(src, out, cipher);
    }

    @Override
    public void decryptFile(String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        FileHelper.copy(src, des, cipher);
    }

    public void decryptFile(DataInputStream in, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, IOException, BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        FileHelper.copy(in, des, cipher);
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

    public String[] findKeySizes() {
        return this.symmetric.findKeySizes();
    }

    public void setKeySize(int keySize) {
        this.symmetric.setKeySize(keySize);
    }

    public SymmetricAlgorithm findSymmetricAlgorithm(String algorithms) {
        return this.symmetric.findSymmetricAlgorithm(algorithms);
    }

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
        encrypted = cipher.doFinal(((IvParameterSpec) this.symmetric.getIv()).getIV());
        out.writeInt(encrypted.length);
        out.write(encrypted);
    }

    public String getSecretKey() {
        return Base64.getEncoder().encodeToString(this.symmetric.getSecretKey().getEncoded());
    }

    public String getIV() {
        return Base64.getEncoder().encodeToString(((IvParameterSpec) this.symmetric.getIv()).getIV());
    }

    public int getKeySize() {
        return this.symmetric.getKeySize();
    }

    public void decryptCopy(Cipher cipher, DataInputStream in) throws IOException, IllegalBlockSizeException, BadPaddingException {
        byte[] rawKeySize = in.readNBytes(in.readInt());
        this.symmetric.setKeySize(Integer.parseInt(new String(cipher.doFinal(rawKeySize), StandardCharsets.UTF_8)));
        byte[] transformation = in.readNBytes(in.readInt());
        SymmetricAlgorithm sa = findSymmetricAlgorithm(new String(cipher.doFinal(transformation), StandardCharsets.UTF_8));
        this.symmetric.setSymmetricAlgorithm(sa);
        byte[] secretKey = in.readNBytes(in.readInt());
        this.symmetric.setSecretKey(new SecretKeySpec(cipher.doFinal(secretKey), this.symmetric.getAlgorithmName()));
        byte[] parameterSpec = in.readNBytes(in.readInt());
        this.symmetric.setAlgorithmParameterSpec(new IvParameterSpec(cipher.doFinal(parameterSpec)));
    }
}
