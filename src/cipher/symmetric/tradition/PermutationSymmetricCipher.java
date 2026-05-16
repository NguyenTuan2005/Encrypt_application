package cipher.symmetric.tradition;

public class PermutationSymmetricCipher extends TraditionSymmetricCipher {
    private int blockSize, modulo;
    private int[] key;
    private String paddingChar;

    @Override
    public void updateConfig(String... args) {
        this.blockSize = Integer.parseInt(args[0]);
        this.key = new int[args[1].length()];
        char[] keyChar = args[1].toCharArray();
        for (int i = 0; i < keyChar.length; i++) {
            key[i] = Integer.parseInt(String.valueOf(keyChar[i]));
        }
        this.paddingChar = args[2];
    }

    @Override
    public String encryptText(String plainText) throws Exception {
        StringBuilder result = new StringBuilder();
        if (key == null || key.length == 0) return "Bạn chưa tạo khóa";
        if (paddingChar == null || paddingChar.isBlank()) return "Bạn chưa tạo ký tự đệm";
        if (isKeyInvalidFormat()) return "Khóa bạn tạo không đúng cấu trúc";
        if (plainText == null) return result.toString();
        if (!language.containValidAlphabet(plainText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        StringBuilder paddingText = new StringBuilder(plainText), sequence;
        int keyindex;

        if ((this.modulo = plainText.length() % blockSize) != 0) {
            for (int i = 0; i < modulo; i++) {
                paddingText.append(paddingChar);
            }
        }

        int loop = paddingText.length() / blockSize, i = 0, start = 0;
        while (i < loop) {
            sequence = new StringBuilder(paddingText.substring(start, start + blockSize));
            String mask = sequence.toString();
            for (int j = 0; j < key.length; j++) {
                keyindex = key[j] - 1;
                char keyTemp = mask.charAt(j);
                sequence.replace(keyindex, keyindex+1, String.valueOf(keyTemp));
            }
            result.append(sequence);
            start += blockSize;
            i++;
        }
        return result.toString();
    }

    private boolean isKeyInvalidFormat() {
        if (key.length != blockSize) {
             return true;
        }
        boolean[] duplicates = new boolean[blockSize + 1];
        for (int digit : key) {
            if (digit < 1 || digit > blockSize || duplicates[digit]) {
                return true;
            }
            duplicates[digit] = true;
        }
        return false;
    }

    @Override
    public String decryptText(String cipherText) throws Exception {
        StringBuilder result = new StringBuilder();
        if (key == null || key.length == 0) return "Bạn chưa tạo khóa";
        if (paddingChar == null || paddingChar.isBlank()) return "Bạn chưa tạo ký tự đệm";
        if (isKeyInvalidFormat()) return "Khóa bạn tạo không đúng cấu trúc";
        if (cipherText == null) return result.toString();
        if (!language.containValidAlphabet(cipherText)) return "Ngôn ngữ hỗ trợ mã hóa không đúng";

        StringBuilder paddingText = new StringBuilder(cipherText), sequence;
        int modulo, keyindex;

        int loop = paddingText.length() / blockSize, i = 0, start = 0;
        while (i < loop) {
            sequence = new StringBuilder(paddingText.substring(start, start + blockSize));
            String mask = sequence.toString();
            for (int j = 0; j < key.length; j++) {
                keyindex = key[j] - 1;
                char keyTemp = mask.charAt(keyindex);
                sequence.replace(j, j+1, String.valueOf(keyTemp));
            }

            if (i + 1 == loop) {
                for (int j = mask.length() - 1; j > mask.length() - this.modulo + 1; j--) {
                    sequence.replace(j,j+1,"");
                }
            }
            result.append(sequence);
            start += blockSize;
            i++;
        }

        return result.toString();
    }

    @Override
    public String filterValid(String text) {
        if (text == null) return "";
        StringBuilder filtered = new StringBuilder();
        for (Character character : text.toCharArray()) {
            if (Character.isDigit(character)) {
                filtered.append(character);
            }
        }
        return filtered.toString();
    }
}
