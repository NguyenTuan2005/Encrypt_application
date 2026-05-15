package cipher.asymmetric;

import utils.FileHelper;
import cipher.TextEncryption;
import cipher.symmetric.modern.ModernSymmetricCipher;
import enums.AsymmetricAlgorithm;
import model.Asymmetric;

import javax.crypto.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class AsymmetricCipher implements TextEncryption {
    protected Asymmetric asymmetric;

    public AsymmetricCipher(AsymmetricAlgorithm algorithm) {
        this.asymmetric = new Asymmetric(algorithm);
    }

    public String[] genKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(asymmetric.getAlgorithmName());
        keyPairGenerator.initialize(asymmetric.getKeySize());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        asymmetric.setPublicKey(keyPair.getPublic());
        asymmetric.setPrivateKey(keyPair.getPrivate());
        return new String[] {Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()), Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded())};
    }

    public void encryptFile(ModernSymmetricCipher modernSymmetricCipher, String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(asymmetric.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, asymmetric.getPublicKey());
        FileHelper.encryptCopy(modernSymmetricCipher, src, des, cipher);
    }

    public void decryptFile(ModernSymmetricCipher modernSymmetricCipher, String src, String des) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, IOException, BadPaddingException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(asymmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, asymmetric.getPrivateKey());
        FileHelper.decryptCopy(modernSymmetricCipher, src, des, cipher);
    }

    @Override
    public String encryptText(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(asymmetric.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, asymmetric.getPublicKey());
        byte[] data = plainText.getBytes(StandardCharsets.UTF_8);
        if (data.length > 32) return "Dữ liệu của bạn vượt quá 32 bytes";
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    @Override
    public String decryptText(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(asymmetric.getTransformation());
        cipher.init(Cipher.DECRYPT_MODE, asymmetric.getPrivateKey());
        byte[] data = Base64.getDecoder().decode(cipherText);
        if (data.length > 32) return "Dữ liệu của bạn vượt quá 32 bytes";
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }

    public String importPublicKey(File src) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.asymmetric.setPublicKey(FileHelper.importPublicKey(src, this.asymmetric.getAlgorithmName()));
        return Base64.getEncoder().encodeToString(this.asymmetric.getPublicKey().getEncoded());
    }

    public boolean exportPublicKey(File des) throws IOException {
        if (this.asymmetric.getPublicKey() == null) return false;
        FileHelper.exportKey(this.asymmetric.getPublicKey().getEncoded(), des);
        return true;
    }

    public String importPrivateKey(File src) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        this.asymmetric.setPrivateKey(FileHelper.importPrivateKey(src, this.asymmetric.getAlgorithmName()));
        return Base64.getEncoder().encodeToString(this.asymmetric.getPublicKey().getEncoded());
    }

    public boolean exportPrivateKey(File des) throws IOException {
        if (this.asymmetric.getPrivateKey() == null) return false;
        FileHelper.exportKey(this.asymmetric.getPrivateKey().getEncoded(), des);
        return true;
    }

    public String[] getTransformations() {
        return this.asymmetric.getTransformations();
    }

    public String[] findKeySizes() {
        return this.asymmetric.findKeySizes();
    }

    public AsymmetricAlgorithm findAsymmetricAlgorithm(String algorithm) {
        return this.asymmetric.findAsymmetricAlgorithm(algorithm);
    }

    public void setKeySize(int keySize) {
        this.asymmetric.setKeySize(keySize);
    }
}
