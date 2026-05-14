package cipher;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class FileHelper {
    public static void copy(String src, String des, Cipher cipher) throws IOException, IllegalBlockSizeException, BadPaddingException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(des));
        CipherInputStream in = new CipherInputStream(input, cipher);

        int i;
        byte[] read = new byte[102400];
        while ((i = in.read(read)) != -1) {
            out.write(read, 0, i);
        }
        read = cipher.doFinal();
        if (read != null)
            out.write(read);
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
        FileOutputStream out = new FileOutputStream(result);
        out.write(key);
        out.flush();
        out.close();
    }

    public static SecretKey importKey(File src, String algorithm) throws IOException {
        FileInputStream in = new FileInputStream(src);
        byte[] key = in.readAllBytes();
        in.close();
        return new SecretKeySpec(key, algorithm);
    }

    public static void exportIV(byte[] iv, File des) throws IOException {
        String result = des.isDirectory() ? des.getPath() + "/iv.txt" : des.getPath();
        FileOutputStream out = new FileOutputStream(result);
        out.write(iv);
        out.flush();
        out.close();
    }

    public static byte[] importIV(File src) throws IOException {
        FileInputStream in = new FileInputStream(src);
        byte[] key = in.readAllBytes();
        in.close();
        return key;
    }
}
