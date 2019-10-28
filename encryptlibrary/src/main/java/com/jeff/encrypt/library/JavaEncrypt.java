package com.jeff.encrypt.library;

import com.jeff.encryption.core.FileIOUtils;
import com.jeff.encryption.core.KeystoreInfo;
import com.jeff.encryption.core.KeystoreUtils;
import com.jeff.encryption.core.RSA;

import java.io.File;
import java.security.KeyPair;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/28.
 */
public class JavaEncrypt extends Encrypt {

    public static void encrypt(KeystoreInfo info, File keyFile, File fromFile, File toFile) throws Exception {
        encrypt(getRawKey(info, keyFile), fromFile, toFile);
    }

    public static void decrypt(KeystoreInfo info, File keyFile, File fromFile, File toFile) throws Exception {
        decrypt(getRawKey(info, keyFile), fromFile, toFile);
    }

    public static byte[] getRawKey(KeystoreInfo info, File keyFile) throws Exception {
        KeyPair pair = KeystoreUtils.getKeyPair(info);
        //解密密钥
        return RSA.decrypt(FileIOUtils.readIn(keyFile), pair.getPublic());
    }
}
