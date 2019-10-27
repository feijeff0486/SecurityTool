package com.jeff.securitytool.demo;

import com.jeff.encryption.core.AES;
import com.jeff.encryption.core.Base64;
import com.jeff.encryption.core.KeystoreInfo;
import com.jeff.encryption.core.RSA;
import com.jeff.encryption.core.KeystoreUtils;

import java.io.File;
import java.security.KeyPair;
import java.util.UUID;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/23.
 */
public final class SignDemo {
    private static StringBuilder processBuilder = new StringBuilder();

    public static void main(String[] args) {
//        randomKey();
        encrypt();
        decrypt();
    }

    /**
     * 生成随机密钥
     */
    private static void randomKey() {
        try {
            processBuilder.append("生成随机密钥:\n");
            //生成随机数作为seed种子
            String uuid = UUID.randomUUID().toString();
            processBuilder.append("uuid = ").append(uuid).append("\n");
            byte[] seed = uuid.getBytes("UTF-8");
            //生成AES秘钥
            byte[] rawkey = AES.getRawKey(seed);

            KeystoreInfo keystoreInfo=new KeystoreInfo.Builder()
                    .setKeystoreName("/Users/zhangfei/Dev/Sign/security.keystore")
                    .setKeystorePassword("hiveviewdomybox")
                    .setAlias("domy_security")
                    .setAliasPassword("hiveviewdomybox")
                    .create();
            //获取应用签名的密钥对
            KeyPair pair = KeystoreUtils.getKeyPair(keystoreInfo);
            //通过RSA私钥来加密AES秘钥
            byte[] key = RSA.encrypt(rawkey, pair.getPrivate());
            //Base64编码成字符串展示
            String base64Key = Base64.encode(key);
            processBuilder.append("随机密钥 = ").append(base64Key).append("\n");

            //将raw key输出
            File file = new File("/Users/zhangfei/Dev/Sign" + File.separator + "security_key.dat");
//            if(!file.getParentFile().exists()){ //如果文件的目录不存在
//                file.getParentFile().mkdirs(); //创建目录
//            }
            if (!file.exists()) {
                boolean result = file.createNewFile();
                processBuilder.append("创建文件: ").append(result).append("\n");
            }
            boolean result = FileIOUtils.writeFileFromString(file, base64Key);
            processBuilder.append("文档写入结果: ").append(result);
//            System.out.println(processBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void encrypt() {
        try {
            File file = new File("/Users/zhangfei/Dev/Sign/security_sample.txt");
            File cryptFile = new File("/Users/zhangfei/Dev/Sign" + File.separator + "security_sample_crypted.txt");
            if (!cryptFile.exists()) {
                boolean result = cryptFile.createNewFile();
                processBuilder.append("生成加密文件: ").append(result).append("\n");
            }
            AES.encryptFile(FileIOUtils.readFile2BytesByStream("/Users/zhangfei/Dev/Sign/security_key.dat"), file, cryptFile);
            System.out.println(processBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void decrypt() {
        processBuilder=new StringBuilder();
        try {
            File cryptFile = new File("/Users/zhangfei/Dev/Sign/security_sample_crypted.txt");
            File file = new File("/Users/zhangfei/Dev/Sign" + File.separator + "security_sample_new.txt");
            if (!file.exists()) {
                boolean result = file.createNewFile();
                processBuilder.append("生成解密文件: ").append(result).append("\n");
            }
            AES.decryptFile(FileIOUtils.readFile2BytesByStream("/Users/zhangfei/Dev/Sign/security_key.dat"), cryptFile, file);
            System.out.println(processBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
