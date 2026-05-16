package model;

import enums.HashAlgorithm;

public class Hash {
    private HashAlgorithm algorithm;

    public Hash(HashAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return this.algorithm.getAlgorithm();
    }

    public String[] getAlgorithms() {
        HashAlgorithm[] values = HashAlgorithm.values();
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = String.valueOf(values[i].getAlgorithm());
        }
        return result;
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
