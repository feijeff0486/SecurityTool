package com.jeff.securitytool.demo;

import com.jeff.encrypt.library.Encrypt;
import com.jeff.encrypt.library.JavaEncrypt;
import com.jeff.encryption.core.Base64;
import com.jeff.encryption.core.FileIOUtils;
import com.jeff.encryption.core.KeystoreInfo;

import java.io.File;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/23.
 */
public final class SignDemo {
    private static StringBuilder processBuilder = new StringBuilder();
    private static KeystoreInfo keystoreInfo;

    public static void main(String[] args) {
        keystoreInfo = getKeystoreInfo();
        outputKey();
        encrypt();
        decrypt();
    }

    /**
     * 生成随机密钥
     */
    private static void outputKey() {
        try {
            //Base64编码成字符串展示
            String base64Key = Base64.encode(Encrypt.randomKey(keystoreInfo));
            processBuilder.append("随机密钥 = ").append(base64Key).append("\n");

            //将raw key输出
            File file = new File("/Users/zhangfei/Dev/Sign" + File.separator + "security_key.dat");
            if (!file.exists()) {
                boolean result = file.createNewFile();
                processBuilder.append("创建文件: ").append(result).append("\n");
            }

            FileIOUtils.writeOut(file, Base64.decode(base64Key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static KeystoreInfo getKeystoreInfo() {
        return new KeystoreInfo.Builder()
                        .setKeystoreName("/Users/zhangfei/Dev/Sign/security.keystore")
                        .setKeystorePassword("hiveviewdomybox")
                        .setAlias("domy_security")
                        .setAliasPassword("hiveviewdomybox")
                        .create();
    }

    private static void encrypt() {
        try {
            File file = new File("/Users/zhangfei/Dev/Sign/security_sample.txt");
            File cryptFile = new File("/Users/zhangfei/Dev/Sign" + File.separator + "security_sample_crypted.txt");
            if (!cryptFile.exists()) {
                boolean result = cryptFile.createNewFile();
                processBuilder.append("生成加密文件: ").append(result).append("\n");
            }
            //获取应用签名的密钥对
//            KeyPair pair = KeystoreUtils.getKeyPair(keystoreInfo);
//            byte[] rawkey=RSA.decrypt(FileIOUtils.readIn(new File("/Users/zhangfei/Dev/Sign/security_key.dat")),pair.getPublic());
//            AES.encryptFile(rawkey, file, cryptFile);

            JavaEncrypt.encrypt(keystoreInfo,new File("/Users/zhangfei/Dev/Sign/security_key.dat"),file,cryptFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void decrypt() {
        try {
            File cryptFile = new File("/Users/zhangfei/Dev/Sign/security_sample_crypted.txt");
            File file = new File("/Users/zhangfei/Dev/Sign" + File.separator + "security_sample_new.txt");
            if (!file.exists()) {
                boolean result = file.createNewFile();
                processBuilder.append("生成解密文件: ").append(result).append("\n");
            }
            //获取应用签名的密钥对
//            KeyPair pair = KeystoreUtils.getKeyPair(keystoreInfo);
//            byte[] rawkey=RSA.decrypt(FileIOUtils.readIn(new File("/Users/zhangfei/Dev/Sign/security_key.dat")),pair.getPublic());
//            AES.decryptFile(rawkey, cryptFile, file);

            JavaEncrypt.decrypt(keystoreInfo,new File("/Users/zhangfei/Dev/Sign/security_key.dat"),cryptFile, file);
            System.out.println(processBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
