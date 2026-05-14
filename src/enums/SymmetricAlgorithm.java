package enums;

import java.util.Arrays;

public enum SymmetricAlgorithm {

    AES_CBC_NOPADDING("AES/CBC/NoPadding", "AES", new int[]{128, 192, 256}, 16),
    AES_CFB_NOPADDING("AES/CFB/NoPadding", "AES", new int[]{128, 192, 256}, 16),
    AES_OFB_NOPADDING("AES/OFB/NoPadding", "AES", new int[]{128, 192, 256}, 16),
    AES_ECB_NOPADDING("AES/ECB/NoPadding", "AES", new int[]{128, 192, 256}, 0),
    AES_GCM_NOPADDING("AES/GCM/NoPadding", "AES", new int[]{128, 192, 256}, 12),

    AES_KW_NOPADDING("AES/KW/NoPadding", "AES", new int[]{128, 192, 256}, 0),
    AES_KW_PKCS5PADDING("AES/KW/PKCS5Padding", "AES", new int[]{128, 192, 256}, 0),
    AES_KWP_NOPADDING("AES/KWP/NoPadding", "AES", new int[]{128, 192, 256}, 0),

    CHACHA20("ChaCha20", "ChaCha20", new int[]{256}, 12),
    CHACHA20_POLY1305("ChaCha20-Poly1305", "ChaCha20", new int[]{256}, 12),

    DES_CBC_NOPADDING("DES/CBC/NoPadding", "DES", new int[]{56}, 8),
    DESEDE_CBC_NOPADDING("DESede/CBC/NoPadding", "DESede", new int[]{112, 168}, 8),

    BLOWFISH_CBC_NOPADDING("Blowfish/CBC/NoPadding", "Blowfish", new int[]{32, 64, 128, 256, 448}, 8),

    RC2_CBC_NOPADDING("RC2/CBC/NoPadding", "RC2", new int[]{40, 64, 128}, 8),

    ARCFOUR("ARCFOUR", "ARCFOUR", new int[]{40, 56, 128, 256}, 0);

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
        return Arrays.stream(values())
                .map(SymmetricAlgorithm::getTransformation)
                .toArray(String[]::new);
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