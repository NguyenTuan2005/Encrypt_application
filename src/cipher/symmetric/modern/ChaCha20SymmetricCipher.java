package cipher.symmetric.modern;

import cipher.FileHelper;
import enums.SymmetricAlgorithm;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ChaCha20SymmetricCipher extends ModernSymmetricCipher {
    public ChaCha20SymmetricCipher(SymmetricAlgorithm algorithm) {
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
        return Base64.getEncoder().encodeToString(((ChaCha20ParameterSpec) this.symmetric.getIv()).getNonce());
    }

    @Override
    public void encryptCopy(Cipher cipher, DataOutputStream out) throws IOException, IllegalBlockSizeException, BadPaddingException {
        out.write(cipher.doFinal(String.valueOf(this.symmetric.getKeySize()).getBytes(StandardCharsets.UTF_8)));
        byte[] transformation = this.symmetric.getTransformation().getBytes(StandardCharsets.UTF_8);
        out.write(cipher.doFinal(transformation));
        out.write(cipher.doFinal(this.symmetric.getSecretKey().getEncoded()));
        out.write(cipher.doFinal(((ChaCha20ParameterSpec) this.symmetric.getIv()).getNonce()));
    }

    @Override
    public String getIV() {
        return Base64.getEncoder().encodeToString(((ChaCha20ParameterSpec) this.symmetric.getIv()).getNonce());
    }
}
