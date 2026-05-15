package cipher.hash;

import enums.HashAlgorithm;
import model.Hash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCipher {
    private Hash hash;

    public HashCipher(HashAlgorithm algorithm) {
        this.hash = new Hash(algorithm);
    }

    public String hashText(String data) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(hash.getAlgorithm());
        byte[] md = messageDigest.digest(data.getBytes());
        BigInteger result = new BigInteger(1, md);
        return result.toString(16);
    }

    public String hashFile(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(hash.getAlgorithm());
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        DigestInputStream in = new DigestInputStream(bis, messageDigest);

        byte[] read = new byte[1024];
        int length;
        do {
            length = in.read(read);
        } while (length != -1);
        BigInteger result = new BigInteger(1, in.getMessageDigest().digest());
        return result.toString(16);
    }

    public String[] getAlgorithms() {
        return this.hash.getAlgorithms();
    }

    public HashAlgorithm findHashAlgorithm(String algorithm) {
        return this.hash.findHashAlgorithm(algorithm);
    }
}
