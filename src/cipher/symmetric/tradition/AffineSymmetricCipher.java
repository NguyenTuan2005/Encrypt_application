package cipher.symmetric.tradition;

public class AffineSymmetricCipher extends TraditionSymmetricCipher {
    private int keyA;
    private int keyB;
    private int modulo;

    @Override
    public void updateConfig(String... args) {
        this.keyA = Integer.parseInt(args[0]);
        this.keyB = Integer.parseInt(args[1]);
        this.modulo = Integer.parseInt(args[2]);
    }

    @Override
    public String encryptText(String plainText) throws Exception {
        StringBuilder result = new StringBuilder();
        int index = -1;
        char cipherChar;

        if (keyA == 0) return "Bạn chưa tạo giá trị a";
        if (keyB == 0) return "Bạn chưa tạo giá trị b";
        if (isKeyInvalidCondition()) return "Giá trị a bạn tạo không phù hợp với giá trị n";
        if (plainText == null) return result.toString();
        if (!language.containValidAlphabet(plainText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        char[] textChar = plainText.toCharArray();
        for (int i = 0; i < textChar.length; i++) {
            if (!Character.isLetter(textChar[i])) {
                result.append(textChar[i]);
                continue;
            }
            index = language.getIndexOf(Character.toUpperCase(textChar[i]));
            index = ((keyA * index) + keyB) % modulo;
            cipherChar = language.getCharAt(index);

            if (Character.isLowerCase(textChar[i])) {
                cipherChar = Character.toLowerCase(cipherChar);
            }
            result.append(cipherChar);
        }
        return result.toString();
    }

    private boolean isKeyInvalidCondition() {
        return gcd(keyA, modulo) != 1;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    @Override
    public String decryptText(String cipherText) throws Exception {
        StringBuilder result = new StringBuilder();
        int index = -1;
        char cipherChar;

        if (keyA == 0) return "Bạn chưa tạo giá trị a";
        if (keyB == 0) return "Bạn chưa tạo giá trị b";
        if (isKeyInvalidCondition()) return "Giá trị a bạn tạo không phù hợp với giá trị n";
        if (cipherText == null) return result.toString();
        if (!language.containValidAlphabet(cipherText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        int keyAInverse = modularInverse(keyA, modulo);
        char[] textChar = cipherText.toCharArray();
        for (int i = 0; i < textChar.length; i++) {
            if (!Character.isLetter(textChar[i])) {
                result.append(textChar[i]);
                continue;
            }
            index = language.getIndexOf(Character.toUpperCase(textChar[i]));
            index = (keyAInverse * (index - keyB)) % modulo;
            if (index < 0) index += modulo;
            cipherChar = language.getCharAt(index);

            if (Character.isLowerCase(textChar[i])) {
                cipherChar = Character.toLowerCase(cipherChar);
            }
            result.append(cipherChar);
        }
        return result.toString();
    }

    private int modularInverse(int keyA, int modulo) {
        for (int i = 1; i < modulo - 1; i++) {
            if ((keyA * i) % modulo == 1) {
                return i;
            }
        }
        return 1;
    }
}
