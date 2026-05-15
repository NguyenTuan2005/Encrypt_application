package cipher.symmetric.modern;

import cipher.FileHelper;
import enums.SymmetricAlgorithm;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
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
    public void encryptCopy(Cipher cipher, DataOutputStream out) throws IOException, IllegalBlockSizeException, BadPaddingException {
        out.write(cipher.doFinal(String.valueOf(this.symmetric.getKeySize()).getBytes(StandardCharsets.UTF_8)));
        byte[] transformation = this.symmetric.getTransformation().getBytes(StandardCharsets.UTF_8);
        out.write(cipher.doFinal(transformation));
        out.write(cipher.doFinal(this.symmetric.getSecretKey().getEncoded()));
    }
}
