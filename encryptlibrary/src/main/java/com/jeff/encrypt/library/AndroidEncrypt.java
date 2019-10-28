package com.jeff.encrypt.library;

import android.content.Context;
import android.content.res.AssetManager;

import com.jeff.encryption.core.AES;
import com.jeff.encryption.core.FileIOUtils;
import com.jeff.encryption.core.RSA;

import java.io.File;
import java.io.InputStream;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/28.
 */
public class AndroidEncrypt extends Encrypt{
    public static final String ENCRYPT_FILE = "encrypt.dat";

    /**
     * 获取解密之后的文件流
     */
    public static InputStream onObtainInputStream(Context context) {
        try {
            AssetManager assetmanager = context.getAssets();
            InputStream is = assetmanager.open("encrypt_测试.txt");

            byte[] rawKey = getRawKey(context);

            //使用解密流，数据写出到基础OutputStream之前先对该会先对数据进行解密
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, AES.AES);
            Cipher cipher = Cipher.getInstance(AES.AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return new CipherInputStream(is, cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void encrypt(Context context, File fromFile, File toFile) throws Exception {
        encrypt(getRawKey(context), fromFile, toFile);
    }

    public static void decrypt(Context context, File fromFile, File toFile) throws Exception {
        decrypt(getRawKey(context), fromFile, toFile);
    }

    /**
     * 获取解密之后的文件加密密钥
     */
    public static byte[] getRawKey(Context context) throws Exception {
        //获取应用的签名密钥
        byte[] sign = SignUtils.getSign(context);
        PublicKey pubKey = SignUtils.getPublicKey(sign);
        //获取加密文件的密钥
        byte[] key = FileIOUtils.readIn(context.getAssets().open(ENCRYPT_FILE));
        //解密密钥
        return RSA.decrypt(key, pubKey);
    }
}
