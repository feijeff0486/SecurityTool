package com.jeff.encryption.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/23.
 */
public final class AES {
    private static final String AES = "AES";
    private static final String ALGORITHM = "SHA1PRNG";

    /**
     * AES算法加密文件
     */
    public static void encryptFile(byte[] rawKey, File fromFile, File toFile) throws Exception {
        byte[] buffer = new byte[512 * 1024 - 16];
        optFile(rawKey, fromFile, toFile, Cipher.ENCRYPT_MODE,buffer);
    }

    /**
     * AES算法解密文件
     */
    public static void decryptFile(byte[] rawKey, File fromFile, File toFile) throws Exception {
        byte[] buffer = new byte[512 * 1024 + 16];
        optFile(rawKey, fromFile, toFile, Cipher.DECRYPT_MODE,buffer);
    }

    private static void optFile(byte[] rawKey, File fromFile, File toFile, int opmode, byte[]buffer) throws Exception {
        if (!fromFile.exists()) {
            throw new NullPointerException("文件不存在");
        }
        if (toFile.exists()) {
            toFile.delete();
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(opmode, secretKeySpec);

        FileInputStream fis = new FileInputStream(fromFile);
        FileOutputStream fos = new FileOutputStream(toFile, true);
        int offset;
        //使用解密流来解密
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        while ((offset = cis.read(buffer)) != -1) {
            fos.write(buffer, 0, offset);
            fos.flush();
        }
        fos.close();
        fis.close();
    }

    /**
     * 生成用AES算法来加密的密钥流，这个密钥会被应用签名{@link KeystoreUtils}的密钥进行二次加密
     */
    public static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance(AES);
        SecureRandom sr = SecureRandom.getInstance(ALGORITHM);
        sr.setSeed(seed);
        //192 and 256 bits may not be available
        generator.init(128, sr);
        SecretKey key = generator.generateKey();
        return key.getEncoded();
    }
}
