package model;

import enums.SymmetricAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;

public class Symmetric {
    private SymmetricAlgorithm algorithm;
    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;
    private int keySize;

    public Symmetric(SymmetricAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.keySize = algorithm.getKeySizes()[0];
    }

    public String getAlgorithmName() {
        String name = algorithm.getTransformation();
        if (name.contains("/")) {
            String[] splitAlgorithm = algorithm.getTransformation().split("/");
            name = splitAlgorithm[0];
        }
        return name;
    }

    public String getTransformation() {
        return algorithm.getTransformation();
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public int[] getKeySizes() {
        return algorithm.getKeySizes();
    }

    public int getIVSize() {
        return algorithm.getIvSize();
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public void setIvParameterSpec(IvParameterSpec ivParameterSpec) {
        this.ivParameterSpec = ivParameterSpec;
    }

    public String[] getAlgorithms() {
        return this.algorithm.getAlgorithms();
    }

    public String[] findKeySizes(String algorithm) {
        for (SymmetricAlgorithm sa : SymmetricAlgorithm.values()) {
            if (sa.getTransformation().equals(algorithm))
                this.algorithm = sa;
        }

        return Arrays.stream(this.algorithm.getKeySizes())
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
    }
}
