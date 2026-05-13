package cipher;

public interface FileEncryption {
    void encryptFile(String src, String des);
    void decryptFile(String src, String des);
}
