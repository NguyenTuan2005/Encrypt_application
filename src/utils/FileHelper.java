package utils;

import cipher.symmetric.modern.ModernSymmetricCipher;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class FileHelper {
    public static void copy(String src, String des, Cipher cipher) throws IOException, IllegalBlockSizeException, BadPaddingException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(des));
        CipherInputStream in = new CipherInputStream(input, cipher);

        int i;
        byte[] read = new byte[1024];
        while ((i = in.read(read)) != -1) {
            out.write(read, 0, i);
        }
        in.close();
        out.flush();
        out.close();
    }

    public static void copy(String src, DataOutputStream dos, Cipher cipher) throws IOException, IllegalBlockSizeException, BadPaddingException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream out = new BufferedOutputStream(dos);
        CipherInputStream in = new CipherInputStream(input, cipher);

        int i;
        byte[] read = new byte[1024];
        while ((i = in.read(read)) != -1) {
            out.write(read, 0, i);
        }
        in.close();
        out.flush();
    }

    public static void copy(DataInputStream dis, String des, Cipher cipher) throws IOException, IllegalBlockSizeException, BadPaddingException {
        BufferedInputStream input = new BufferedInputStream(dis);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(des));
        CipherInputStream in = new CipherInputStream(input, cipher);

        int i;
        byte[] read = new byte[1024];
        while ((i = in.read(read)) != -1) {
            out.write(read, 0, i);
        }
        in.close();
        out.flush();
        out.close();
    }

    public static String getExtension(File file) {
        String result = file.getName();
        int dotIndex = result.lastIndexOf('.');

        if (dotIndex == -1 || dotIndex == result.length() - 1)
            return ".crypt";
        return result.substring(dotIndex);
    }

    public static void exportKey(byte[] key, File des) throws IOException {
        String result = des.isDirectory() ? des.getPath() + "/key.txt" : des.getPath();
        PrintWriter out = new PrintWriter(result);
        out.write(Base64.getEncoder().encodeToString(key));
        out.flush();
        out.close();
    }

    public static SecretKey importKey(File src, String algorithm) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(src));
        String key = in.readLine();
        in.close();
        return new SecretKeySpec(Base64.getDecoder().decode(key), algorithm);
    }

    public static void exportIV(byte[] iv, File des) throws IOException {
        String result = des.isDirectory() ? des.getPath() + "/iv.txt" : des.getPath();
        PrintWriter out = new PrintWriter(result);
        out.write(Base64.getEncoder().encodeToString(iv));
        out.flush();
        out.close();
    }

    public static byte[] importIV(File src) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(src));
        String iv = in.readLine();
        in.close();
        return Base64.getDecoder().decode(iv);
    }


    public static void encryptCopy(ModernSymmetricCipher symmetricCipher, String src, String des, Cipher cipher) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(des));
        symmetricCipher.encryptCopy(cipher, out);
        symmetricCipher.encryptFile(src, out);

        out.flush();
        out.close();
    }

    public static void decryptCopy(ModernSymmetricCipher symmetricCipher, String src, String des, Cipher cipher) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        DataInputStream in = new DataInputStream(new FileInputStream(src));
        symmetricCipher.decryptCopy(cipher, in);
        symmetricCipher.decryptFile(in, des);

        in.close();
    }

    public static PublicKey importPublicKey(File src, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        BufferedReader in = new BufferedReader(new FileReader(src));
        String key = in.readLine();
        in.close();

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey importPrivateKey(File src, String algorithm) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        BufferedReader in = new BufferedReader(new FileReader(src));
        String key = in.readLine();
        in.close();

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(keySpec);
    }

    public static String importTraditionKey(File src) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(src));
        String key = in.readLine();
        in.close();

        return key;
    }

    public static void exportTraditionKey(String key, File des) throws FileNotFoundException {
        String result = des.isDirectory() ? des.getPath() + "/tradition.txt" : des.getPath();
        PrintWriter out = new PrintWriter(result);
        out.write(key);
        out.flush();
        out.close();
    }
}
