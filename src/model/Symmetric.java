package model;

import enums.SymmetricAlgorithm;

import javax.crypto.SecretKey;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class Symmetric {
    private SymmetricAlgorithm algorithm;
    private SecretKey secretKey;
    private AlgorithmParameterSpec parameterSpec;
    private int keySize;

    public Symmetric(SymmetricAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.keySize = algorithm.getKeySizes()[0];
    }

    public String getAlgorithmName() {
        return algorithm.getAlgorithm();
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

    public int getParameterSpecSize() {
        return algorithm.getParameterSpecSize();
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public void setAlgorithmParameterSpec(AlgorithmParameterSpec parameterSpec) {
        this.parameterSpec = parameterSpec;
    }

    public String[] getAlgorithms() {
        return this.algorithm.getAlgorithms();
    }

    public AlgorithmParameterSpec getIv() {
        return this.parameterSpec;
    }

    public String[] findKeySizes() {
        if (this.algorithm == null)
            return new String[] {"128"};

        this.keySize = this.algorithm.getKeySizes()[0];

        String[] result = new String[this.algorithm.getKeySizes().length];
        for (int i = 0; i < this.algorithm.getKeySizes().length; i++) {
            result[i] = String.valueOf(this.algorithm.getKeySizes()[i]);
        }
        return result;
    }

    public SymmetricAlgorithm findSymmetricAlgorithm(String algorithm) {
        for (SymmetricAlgorithm sa : SymmetricAlgorithm.values()) {
            if (sa.getTransformation().equals(algorithm)) {
                this.algorithm = sa;
                return sa;
            }
        }
        return null;
    }

    public void setSymmetricAlgorithm(SymmetricAlgorithm sa) {
        this.algorithm = sa;
    }
}
