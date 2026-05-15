package model;

import enums.HashAlgorithm;

import java.util.Arrays;

public class Hash {
    private HashAlgorithm algorithm;

    public Hash(HashAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return this.algorithm.getAlgorithm();
    }

    public String[] getAlgorithms() {
        return Arrays.stream(HashAlgorithm.values())
                .map(HashAlgorithm::getAlgorithm)
                .toArray(String[]::new);
    }

    public HashAlgorithm findHashAlgorithm(String algorithm) {
        for (HashAlgorithm hashAlgorithm : HashAlgorithm.values()) {
            if (hashAlgorithm.getAlgorithm().equals(algorithm)) {
                return hashAlgorithm;
            }
        }
        return null;
    }
}
