package com.jeff.encrypt.library;

import com.jeff.encryption.core.AES;
import com.jeff.encryption.core.KeystoreInfo;
import com.jeff.encryption.core.KeystoreUtils;
import com.jeff.encryption.core.RSA;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.util.UUID;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/28.
 */
public class Encrypt {

    public static byte[] randomKey(KeystoreInfo info) {
        try {
            //生成随机数作为seed种子
            String uuid = UUID.randomUUID().toString();
            byte[] seed = new byte[0];
            seed = uuid.getBytes("UTF-8");
            //生成AES秘钥
            byte[] rawKey = AES.getRawKey(seed);

            //获取应用签名的密钥对
            KeyPair pair = KeystoreUtils.getKeyPair(info);
            //通过RSA私钥来加密AES秘钥
            return RSA.encrypt(rawKey, pair.getPrivate());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void encrypt(byte[] rawKey, File fromFile, File toFile) throws Exception {
        AES.encryptFile(rawKey, fromFile, toFile);
    }

    public static void decrypt(byte[] rawKey, File fromFile, File toFile) throws Exception {
        AES.decryptFile(rawKey, fromFile, toFile);
    }

}
