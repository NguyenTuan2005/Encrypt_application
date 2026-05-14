package cipher.symmetric.modern;

import cipher.FileHelper;
import enums.SymmetricAlgorithm;

import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ChaCha20Symmetric extends ModernSymmetric {
    public ChaCha20Symmetric(SymmetricAlgorithm algorithm) {
        super(algorithm);
    }

    @Override
    public String genIV() {
        ChaCha20ParameterSpec chaCha20ParameterSpec = new ChaCha20ParameterSpec(new byte[symmetric.getParameterSpecSize()], 0);
        symmetric.setAlgorithmParameterSpec(chaCha20ParameterSpec);
        return Base64.getEncoder().encodeToString(chaCha20ParameterSpec.getNonce());
    }

    @Override
    public boolean exportIV(File des) throws IOException {
        if (this.symmetric.getIv() == null) return false;
        FileHelper.exportIV(((ChaCha20ParameterSpec) this.symmetric.getIv()).getNonce(), des);
        return true;
    }

    @Override
    public String importIV(File src) throws IOException {
        byte[] iv = FileHelper.importIV(src);
        this.symmetric.setAlgorithmParameterSpec(new ChaCha20ParameterSpec(iv, 0));
        return Base64.getEncoder().encodeToString(((IvParameterSpec) this.symmetric.getIv()).getIV());
    }
}
