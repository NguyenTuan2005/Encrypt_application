package cipher.symmetric.tradition;

public class VigenereSymmetricCipher extends TraditionSymmetricCipher {
    private String keyword;

    @Override
    public void updateConfig(String... args) {
        this.keyword = args[0];
    }

    @Override
    public String encryptText(String plainText) throws Exception {
        StringBuilder result = new StringBuilder();
        int index, keyindex;
        char cipherChar;
        if (keyword == null || keyword.isBlank()) return "Bạn chưa tạo khóa";
        if (isWrongKey()) return "Khóa bạn tạo không đúng ngôn ngữ hỗ trợ";
        if (plainText == null) return result.toString();
        if (!language.containValidAlphabet(plainText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        char[] textChar = plainText.toCharArray();
        char[] keyChar = keyword.toCharArray();
        int i = 0;
        while (i < textChar.length) {
            for (int j = 0; j < keyChar.length; j++) {
                if (i >= textChar.length) return result.toString();
                if (!Character.isLetter(textChar[i])) {
                    result.append(textChar[i]);
                    i++;
                    continue;
                }
                index = language.getIndexOf(Character.toUpperCase(textChar[i]));
                keyindex = language.getIndexOf(Character.toUpperCase(keyChar[j]));
                index += keyindex;
                index %= language.getAlphabetSize();
                cipherChar = language.getCharAt(index);

                if (Character.isLowerCase(textChar[i])) {
                    cipherChar = Character.toLowerCase(cipherChar);
                }
                result.append(cipherChar);
                i++;
            }
        }
        return result.toString();
    }

    private boolean isWrongKey() {
        return !language.containValidAlphabet(keyword);
    }

    @Override
    public String decryptText(String cipherText) throws Exception {
        StringBuilder result = new StringBuilder();
        int index, keyindex;
        char cipherChar;
        if (keyword == null || keyword.isBlank()) return "Bạn chưa tạo khóa";
        if (isWrongKey()) return "Khóa bạn tạo không đúng ngôn ngữ hỗ trợ";
        if (cipherText == null) return result.toString();
        if (!language.containValidAlphabet(cipherText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        char[] textChar = cipherText.toCharArray();
        char[] keyChar = keyword.toCharArray();
        int i = 0;
        while (i < textChar.length) {
            for (int j = 0; j < keyChar.length; j++) {
                if (i >= textChar.length) return result.toString();
                if (!Character.isLetter(textChar[i])) {
                    result.append(textChar[i]);
                    i++;
                    continue;
                }
                index = language.getIndexOf(Character.toUpperCase(textChar[i]));
                keyindex = language.getIndexOf(Character.toUpperCase(keyChar[j]));
                index -= keyindex;
                index += language.getAlphabetSize();
                index %= language.getAlphabetSize();
                cipherChar = language.getCharAt(index);

                if (Character.isLowerCase(textChar[i])) {
                    cipherChar = Character.toLowerCase(cipherChar);
                }
                result.append(cipherChar);
                i++;
            }
        }
        return result.toString();
    }

    @Override
    public String filterValid(String text) {
        if (text == null) return "";
        StringBuilder filtered = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c) && language.contains(Character.toUpperCase(c))) {
                filtered.append(c);
            }
        }
        return filtered.toString();
    }
}
