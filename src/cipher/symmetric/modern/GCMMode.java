package cipher.symmetric.modern;

import cipher.FileHelper;
import enums.SymmetricAlgorithm;

import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class GCMMode extends ModernSymmetric{
    public GCMMode(SymmetricAlgorithm algorithm) {
        super(algorithm);
    }

    @Override
    public String genIV() {
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, new byte[symmetric.getParameterSpecSize()]);
        symmetric.setAlgorithmParameterSpec(gcmParameterSpec);
        return Base64.getEncoder().encodeToString(gcmParameterSpec.getIV());
    }

    @Override
    public boolean exportIV(File des) throws IOException {
        if (this.symmetric.getIv() == null) return false;
        FileHelper.exportIV(((GCMParameterSpec) this.symmetric.getIv()).getIV(), des);
        return true;
    }

    @Override
    public String importIV(File src) throws IOException {
        byte[] iv = FileHelper.importIV(src);
        this.symmetric.setAlgorithmParameterSpec(new GCMParameterSpec(128, iv));
        return Base64.getEncoder().encodeToString(((IvParameterSpec) this.symmetric.getIv()).getIV());
    }
}
