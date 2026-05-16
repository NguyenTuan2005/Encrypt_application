package enums;

public enum AsymmetricAlgorithm {
    RSA_ECB_PKCS1PADDING("RSA/ECB/PKCS1Padding", "RSA", new int[]{1024, 2048}),
    RSA_ECB_OAEP_SHA1_MGF1("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "RSA", new int[]{1024, 2048}),
    RSA_ECB_OAEP_SHA256_MGF1("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "RSA", new int[]{1024, 2048});

    private final String transformation;
    private final String algorithm;
    private final int[] keySizes;

    AsymmetricAlgorithm(String transformation, String algorithm, int[] keySizes) {
        this.transformation = transformation;
        this.algorithm = algorithm;
        this.keySizes = keySizes;
    }

    public String getTransformation() {
        return transformation;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int[] getKeySizes() {
        return keySizes;
    }

    public String[] getTransformations() {
        AsymmetricAlgorithm[] values = AsymmetricAlgorithm.values();
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = String.valueOf(values[i].getTransformation());
        }
        return result;
    }
}
