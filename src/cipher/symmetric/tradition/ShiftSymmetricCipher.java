package cipher.symmetric.tradition;

public class ShiftSymmetricCipher extends TraditionSymmetricCipher {
    private int shiftValue;

    public ShiftSymmetricCipher(int shiftValue) {
        super();
        this.shiftValue = shiftValue;
    }

    @Override
    public String encryptText(String plainText) throws Exception {
        StringBuilder result = new StringBuilder();
        int index = -1;
        char cipherChar;
        if (plainText == null) return result.toString();
        if (language.containInvalidAlphabet(plainText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        char[] textChar = plainText.toCharArray();
        for (int i = 0; i < textChar.length; i++) {
            if (!Character.isLetterOrDigit(textChar[i])) {
                result.append(textChar[i]);
                continue;
            }
            if (Character.isDigit(textChar[i])) {
                index = language.getIndexDigitOf(textChar[i]);
                index += shiftValue;
                index %= 10;
                cipherChar = language.getDigitAt(index);
                result.append(cipherChar);
                continue;
            }
            index = language.getIndexOf(Character.toUpperCase(textChar[i]));
            index += shiftValue;
            index %= language.getAlphabetSize();

            cipherChar = language.getCharAt(index);

            if (Character.isLowerCase(textChar[i])) {
                cipherChar = Character.toLowerCase(cipherChar);
            }
            result.append(cipherChar);
        }
        return result.toString();
    }

    @Override
    public String decryptText(String cipherText) throws Exception {
        StringBuilder result = new StringBuilder();
        int index = -1;
        char cipherChar;
        if (cipherText == null) return result.toString();
        if (language.containInvalidAlphabet(cipherText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        char[] textChar = cipherText.toCharArray();
        for (int i = 0; i < textChar.length; i++) {
            if (!Character.isLetterOrDigit(textChar[i])) {
                result.append(textChar[i]);
                continue;
            }
            if (Character.isDigit(textChar[i])) {
                index = language.getIndexDigitOf(textChar[i]);
                index -= shiftValue;
                index += 10;
                index %= 10;
                cipherChar = language.getDigitAt(index);
                result.append(cipherChar);
                continue;
            }
            index = language.getIndexOf(Character.toUpperCase(textChar[i]));
            index -= shiftValue;
            index += language.getAlphabetSize();
            index %= language.getAlphabetSize();
            cipherChar = language.getCharAt(index);

            if (Character.isLowerCase(textChar[i])) {
                cipherChar = Character.toLowerCase(cipherChar);
            }
            result.append(cipherChar);
        }
        return result.toString();
    }

    @Override
    public void updateConfig(String... args) {
        this.shiftValue = Integer.parseInt(args[0]);
    }
}
