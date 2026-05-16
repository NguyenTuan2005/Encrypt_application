package model;

import enums.AsymmetricAlgorithm;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

public class Asymmetric {
    private AsymmetricAlgorithm algorithm;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private int keySize;

    public Asymmetric(AsymmetricAlgorithm algorithm) {
        this.algorithm = algorithm;
        this.keySize = algorithm.getKeySizes()[0];
    }

    public String getAlgorithmName() {
        return algorithm.getAlgorithm();
    }

    public int getKeySize() {
        return keySize;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String[] getTransformations() {
        return algorithm.getTransformations();
    }

    public String[] findKeySizes() {
        if (this.algorithm == null)
            return new String[] {"2048"};

        this.keySize = this.algorithm.getKeySizes()[0];

        String[] result = new String[this.algorithm.getKeySizes().length];
        for (int i = 0; i < this.algorithm.getKeySizes().length; i++) {
            result[i] = String.valueOf(this.algorithm.getKeySizes()[i]);
        }
        return result;
    }

    public AsymmetricAlgorithm findAsymmetricAlgorithm(String algorithm) {
        for (AsymmetricAlgorithm asymmetricAlgorithm : AsymmetricAlgorithm.values()) {
            if (asymmetricAlgorithm.getTransformation().equals(algorithm)) {
                this.algorithm = asymmetricAlgorithm;
                return asymmetricAlgorithm;
            }
        }
        return null;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public String getTransformation() {
        return this.algorithm.getTransformation();
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
