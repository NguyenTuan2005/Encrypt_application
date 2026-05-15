package cipher.symmetric.modern;

import utils.FileHelper;
import enums.SymmetricAlgorithm;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class NoIVSymmetricCipher extends ModernSymmetricCipher {
    public NoIVSymmetricCipher(SymmetricAlgorithm algorithm) {
        super(algorithm);
    }

    @Override
    public String genIV() {
        return "Thuật toán này không sử dụng IV";
    }

    @Override
    public boolean exportIV(File des) {
        return false;
    }

    @Override
    public String importKey(File src) {
        return "Thuật toán này không sử dụng IV";
    }

    @Override
    public String getIV() {
        return "Thuật toán này không sử dụng IV";
    }

    @Override
    public String encryptText(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, symmetric.getSecretKey());
        byte[] data = plainText.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    @Override
    public String decryptText(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey());
        byte[] data = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }

    @Override
    public void encryptFile(String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, symmetric.getSecretKey());
        FileHelper.copy(src, des, cipher);
    }

    @Override
    public void decryptFile(String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey());
        FileHelper.copy(src, des, cipher);
    }

    @Override
    public void encryptFile(String src, DataOutputStream out) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, IOException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, symmetric.getSecretKey());
        FileHelper.copy(src, out, cipher);
    }

    @Override
    public void decryptFile(DataInputStream in, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, IOException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey());
        FileHelper.copy(in, des, cipher);
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
    }
}
