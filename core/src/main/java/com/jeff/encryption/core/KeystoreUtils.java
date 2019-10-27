package com.jeff.encryption.core;

import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/23.
 */
public final class KeystoreUtils {

    /**
     * 获取签名的密钥对，用来给密钥加密
     */
    public static KeyPair getKeyPair(KeystoreInfo info) {
        try {
            File storeFile = new File(info.getKeystoreName());
            if (!storeFile.exists()) {
                throw new IllegalArgumentException("未设置签名文件!");
            }
            String keyStoreType = "JKS";
            char[] keystorePassword = info.getKeystorePassword().toCharArray();
            char[] keyaliasPassword = info.getAliasPassword().toCharArray();
            KeyStore keystore = KeyStore.getInstance(keyStoreType);
            keystore.load(new FileInputStream(storeFile), keystorePassword);

            Key key = keystore.getKey(info.getAlias(), keyaliasPassword);
            if (key instanceof PrivateKey) {
                Certificate cert = keystore.getCertificate(info.getAlias());
                PublicKey publicKey = cert.getPublicKey();
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
