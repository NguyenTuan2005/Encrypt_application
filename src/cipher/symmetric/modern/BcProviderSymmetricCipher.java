package cipher.symmetric.modern;

import cipher.FileHelper;
import enums.SymmetricAlgorithm;

import javax.crypto.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

public class BcProviderSymmetricCipher extends ModernSymmetricCipher {
    public BcProviderSymmetricCipher(SymmetricAlgorithm algorithm) {
        super(algorithm);
    }

    @Override
    public String genKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(symmetric.getAlgorithmName(), "BC");
        keyGenerator.init(symmetric.getKeySize());
        SecretKey secretKey = keyGenerator.generateKey();
        symmetric.setSecretKey(secretKey);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    @Override
    public void encryptFile(String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        FileHelper.copy(src, des, cipher);
    }

    @Override
    public void decryptFile(String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        FileHelper.copy(src, des, cipher);
    }

    @Override
    public String encryptText(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        byte[] data = plainText.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    @Override
    public String decryptText(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(symmetric.getTransformation(), "BC");
        cipher.init(Cipher.DECRYPT_MODE, symmetric.getSecretKey(), symmetric.getIv());
        byte[] data = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }
}
