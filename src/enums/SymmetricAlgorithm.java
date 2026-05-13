package enums;

import java.util.Arrays;

public enum SymmetricAlgorithm {

    AES_CBC_NOPADDING("AES/CBC/NoPadding", new int[]{128, 192, 256}, 16),
    AES_CFB_NOPADDING("AES/CFB/NoPadding", new int[]{128, 192, 256}, 16),
    AES_OFB_NOPADDING("AES/OFB/NoPadding", new int[]{128, 192, 256}, 16),
    AES_ECB_NOPADDING("AES/ECB/NoPadding", new int[]{128, 192, 256}, 0),
    AES_GCM_NOPADDING("AES/GCM/NoPadding", new int[]{128, 192, 256}, 12),

    AES_KW_NOPADDING("AES/KW/NoPadding", new int[]{128, 192, 256}, 0),
    AES_KW_PKCS5PADDING("AES/KW/PKCS5Padding", new int[]{128, 192, 256}, 0),
    AES_KWP_NOPADDING("AES/KWP/NoPadding", new int[]{128, 192, 256}, 0),

    CHACHA20("ChaCha20", new int[]{256}, 12),
    CHACHA20_POLY1305("ChaCha20-Poly1305", new int[]{256}, 12),

    DES_CBC_NOPADDING("DES/CBC/NoPadding", new int[]{56}, 8),
    DESEDE_CBC_NOPADDING("DESede/CBC/NoPadding", new int[]{112, 168}, 8),
    DESEDEWRAP("DESedeWrap", new int[]{112, 168}, 0),

    BLOWFISH_CBC_NOPADDING("Blowfish/CBC/NoPadding", new int[]{32, 64, 128, 256, 448}, 8),

    RC2_CBC_NOPADDING("RC2/CBC/NoPadding", new int[]{40, 64, 128}, 8),

    ARCFOUR("ARCFOUR", new int[]{40, 56, 128, 256}, 0),

    PBEWITHMD5ANDDES("PBEWithMD5AndDES", new int[]{56}, 8),
    PBEWITHMD5ANDTRIPLEDES("PBEWithMD5AndTripleDES", new int[]{112, 168}, 8),

    PBEWITHSHA1ANDDESEDE("PBEWithSHA1AndDESede", new int[]{112, 168}, 8),
    PBEWITHSHA1ANDRC2_40("PBEWithSHA1AndRC2_40", new int[]{40}, 8),
    PBEWITHSHA1ANDRC2_128("PBEWithSHA1AndRC2_128", new int[]{128}, 8),
    PBEWITHSHA1ANDRC4_40("PBEWithSHA1AndRC4_40", new int[]{40}, 0),
    PBEWITHSHA1ANDRC4_128("PBEWithSHA1AndRC4_128", new int[]{128}, 0),

    PBEWITHHMACSHA1ANDAES_128("PBEWithHmacSHA1AndAES_128", new int[]{128}, 16),
    PBEWITHHMACSHA1ANDAES_256("PBEWithHmacSHA1AndAES_256", new int[]{256}, 16),

    PBEWITHHMACSHA224ANDAES_128("PBEWithHmacSHA224AndAES_128", new int[]{128}, 16),
    PBEWITHHMACSHA224ANDAES_256("PBEWithHmacSHA224AndAES_256", new int[]{256}, 16),

    PBEWITHHMACSHA256ANDAES_128("PBEWithHmacSHA256AndAES_128", new int[]{128}, 16),
    PBEWITHHMACSHA256ANDAES_256("PBEWithHmacSHA256AndAES_256", new int[]{256}, 16),

    PBEWITHHMACSHA384ANDAES_128("PBEWithHmacSHA384AndAES_128", new int[]{128}, 16),
    PBEWITHHMACSHA384ANDAES_256("PBEWithHmacSHA384AndAES_256", new int[]{256}, 16),

    PBEWITHHMACSHA512ANDAES_128("PBEWithHmacSHA512AndAES_128", new int[]{128}, 16),
    PBEWITHHMACSHA512ANDAES_256("PBEWithHmacSHA512AndAES_256", new int[]{256}, 16),

    PBEWITHHMACSHA512_224ANDAES_128("PBEWithHmacSHA512/224AndAES_128", new int[]{128}, 16),
    PBEWITHHMACSHA512_224ANDAES_256("PBEWithHmacSHA512/224AndAES_256", new int[]{256}, 16),

    PBEWITHHMACSHA512_256ANDAES_128("PBEWithHmacSHA512/256AndAES_128", new int[]{128}, 16),
    PBEWITHHMACSHA512_256ANDAES_256("PBEWithHmacSHA512/256AndAES_256", new int[]{256}, 16);

    private final String transformation;
    private final int[] keySize;
    private final int ivSize;

    SymmetricAlgorithm(String transformation, int[] keySize, int ivSize) {
        this.transformation = transformation;
        this.keySize = keySize;
        this.ivSize = ivSize;
    }

    public String[] getAlgorithms() {
        return Arrays.stream(values())
                .map(SymmetricAlgorithm::getTransformation)
                .toArray(String[]::new);
    }

    public String getTransformation() {
        return transformation;
    }

    public int[] getKeySizes() {
        return keySize;
    }

    public int getIvSize() {
        return ivSize;
    }

}