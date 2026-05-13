package cipher.symmetric.modern;

import cipher.FileEncryption;
import cipher.TextEncryption;
import enums.SymmetricAlgorithm;
import model.Symmetric;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class ModernSymmetric implements TextEncryption, FileEncryption {
    protected Symmetric symmetric;

    public ModernSymmetric(SymmetricAlgorithm algorithm) {
        this.symmetric = new Symmetric(algorithm);
    }

    public String genKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(symmetric.getAlgorithmName());
        keyGenerator.init(symmetric.getKeySize());
        SecretKey secretKey = keyGenerator.generateKey();
        symmetric.setSecretKey(secretKey);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    };

    public String genIV() {
        if (symmetric.getIVSize() == -1) return "Thuật toán này không sử dụng IV";
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[symmetric.getKeySize()]);
        symmetric.setIvParameterSpec(ivParameterSpec);
        return Base64.getEncoder().encodeToString(ivParameterSpec.getIV());
    };

    @Override
    public void encryptFile(String src, String des) {

    }

    @Override
    public void decryptFile(String src, String des) {

    }

    @Override
    public String encryptText(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, symmetric.getSecretKey());
        byte[] data = plainText.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    @Override
    public String decryptText(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey());
        byte[] data = cipherText.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    public SecretKey importKey() {
        return null;
    }

    public void exportKey() {
    }

    public IvParameterSpec importIV() {
        return null;
    }

    public void exportIV() {
    }

    public String[] getAlgorithms() {
        return this.symmetric.getAlgorithms();
    }

    public String[] getKeySizes() {
        return Arrays.stream(this.symmetric.getKeySizes())
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
    }

    public String[] findKeySizes(String algorithm) {
        return this.symmetric.findKeySizes(algorithm);
    }

    public void setKeySize(int keySize) {
        this.symmetric.setKeySize(keySize);
    }
}
