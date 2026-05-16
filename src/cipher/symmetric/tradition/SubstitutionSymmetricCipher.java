package cipher.symmetric.tradition;

import utils.FileHelper;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SubstitutionSymmetricCipher extends TraditionSymmetricCipher {
    private String mapKey;

    @Override
    public void updateConfig(String... args) {
        this.mapKey = args[0];
    }

    @Override
    public String encryptText(String plainText) throws Exception {
        StringBuilder result = new StringBuilder();
        int index = -1;
        char cipherChar;
        if (mapKey == null || mapKey.isBlank()) return "Bạn chưa tạo khóa";
        if (isWrongKey()) return "Khóa bạn tạo không đúng ngôn ngữ hỗ trợ";
        if (plainText == null) return result.toString();
        if (!language.containValidAlphabet(plainText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        char[] textChar = plainText.toCharArray();
        for (int i = 0; i < textChar.length; i++) {
            if (!Character.isLetter(textChar[i])) {
                result.append(textChar[i]);
                continue;
            }
            index = getIndexOf(Character.toUpperCase(textChar[i]));
            cipherChar = language.getCharAt(index);

            if (Character.isLowerCase(textChar[i])) {
                cipherChar = Character.toLowerCase(cipherChar);
            }
            result.append(cipherChar);
        }
        return result.toString();
    }

    private boolean isWrongKey() {
        return !language.containValidAlphabet(mapKey);
    }

    private int getIndexOf(char data) {
        return mapKey.indexOf(data);
    }

    @Override
    public String decryptText(String cipherText) throws Exception {
        StringBuilder result = new StringBuilder();
        int index = -1;
        char cipherChar;
        if (mapKey == null || mapKey.isBlank()) return "Bạn chưa tạo khóa";
        if (cipherText == null) return result.toString();
        if (!language.containValidAlphabet(cipherText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        char[] textChar = cipherText.toCharArray();
        for (int i = 0; i < textChar.length; i++) {
            if (!Character.isLetter(textChar[i])) {
                result.append(textChar[i]);
                continue;
            }
            index = language.getIndexOf(Character.toUpperCase(textChar[i]));
            cipherChar = getCharAt(index);

            if (Character.isLowerCase(textChar[i])) {
                cipherChar = Character.toLowerCase(cipherChar);
            }
            result.append(cipherChar);
        }
        return result.toString();
    }

    private char getCharAt(int index) {
        return mapKey.charAt(index);
    }

    @Override
    public String genKey() {
        String alphabet = language.getAlphabet();
        char[] chars = alphabet.toCharArray();
        Random random = new Random();
        char temp;

        for (int i = 0; i < chars.length; i++) {
            int j = random.nextInt(i, chars.length);
            temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        this.mapKey = new String(chars);
        return mapKey;
    }

    @Override
    public String exportKey(File des) throws IOException {
        if (this.mapKey == null)
            return "Không thể xuất khóa cho bạn";
        FileHelper.exportTraditionKey(mapKey, des);
        return "Đã xuất khóa cho bạn";
    }

    @Override
    public String importKey(File src) throws IOException {
        this.mapKey = FileHelper.importTraditionKey(src);
        return mapKey;
    }
}
