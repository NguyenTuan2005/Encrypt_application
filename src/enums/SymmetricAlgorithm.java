package enums;

public enum SymmetricAlgorithm {

    AES_CBC_PKCS5PADDING("AES/CBC/PKCS5Padding", "AES", new int[]{128, 192, 256}, 16),
    AES_CFB_PKCS5PADDING("AES/CFB/PKCS5Padding", "AES", new int[]{128, 192, 256}, 16),
    AES_OFB_PKCS5PADDING("AES/OFB/PKCS5Padding", "AES", new int[]{128, 192, 256}, 16),
    AES_ECB_PKCS5PADDING("AES/ECB/PKCS5Padding", "AES", new int[]{128, 192, 256}, 0),
    AES_GCM_NOPADDING("AES/GCM/NoPadding", "AES", new int[]{128, 192, 256}, 12),

    AES_KW_PKCS5PADDING("AES/KW/PKCS5Padding", "AES", new int[]{128, 192, 256}, 0),
    AES_KWP_NOPADDING("AES/KWP/NoPadding", "AES", new int[]{128, 192, 256}, 0),

    CHACHA20("ChaCha20", "ChaCha20", new int[]{256}, 12),
    CHACHA20_POLY1305("ChaCha20-Poly1305", "ChaCha20", new int[]{256}, 12),

    DES_CBC_PKCS5PADDING("DES/CBC/PKCS5Padding", "DES", new int[]{56}, 8),
    DESEDE_CBC_PKCS5PADDING("DESede/CBC/PKCS5Padding", "DESede", new int[]{112, 168}, 8),

    BLOWFISH_CBC_PKCS5PADDING("Blowfish/CBC/PKCS5Padding", "Blowfish", new int[]{32, 64, 128, 256, 448}, 8),

    RC2_CBC_PKCS5PADDING("RC2/CBC/PKCS5Padding", "RC2", new int[]{40, 64, 128}, 8),

    ARCFOUR("ARCFOUR", "ARCFOUR", new int[]{40, 56, 128, 256}, 0),

    CAMELLIA_CBC_PKCS5PADDING("Camellia/CBC/PKCS5Padding", "Camellia", new int[]{128, 192, 256}, 16),
    CAST5_CBC_PKCS5PADDING("CAST5/CBC/PKCS5Padding", "CAST5", new int[]{40, 80, 128}, 8),
    TWOFISH_CBC_PKCS5PADDING("Twofish/CBC/PKCS5Padding", "Twofish", new int[]{128, 192, 256}, 16);
    private final String transformation;
    private final String algorithm;
    private final int[] keySize;
    private final int parameterSpecSize;

    SymmetricAlgorithm(String transformation, String algorithm, int[] keySize, int parameterSpecSize) {
        this.transformation = transformation;
        this.algorithm = algorithm;
        this.keySize = keySize;
        this.parameterSpecSize = parameterSpecSize;
    }

    public String[] getAlgorithms() {
        SymmetricAlgorithm[] values = SymmetricAlgorithm.values();
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = String.valueOf(values[i].getTransformation());
        }
        return result;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getTransformation() {
        return transformation;
    }

    public int[] getKeySizes() {
        return keySize;
    }

    public int getParameterSpecSize() {
        return parameterSpecSize;
    }
}