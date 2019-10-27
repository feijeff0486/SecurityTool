package com.jeff.securitytool.demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/23.
 */
public final class SignUtils {

    /**
     * 获取当前应用的签名
     *
     * @param context
     */
    public static byte[] getSign(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = info.signatures;
            if (signatures != null) {
                return signatures[0].toByteArray();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据签名去获取公钥
     * @param signature
     * @return
     */
    public static PublicKey getPublicKey(byte[] signature) {
        try {
            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature));
            return cert.getPublicKey();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
