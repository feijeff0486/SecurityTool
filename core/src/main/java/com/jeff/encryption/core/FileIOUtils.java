package com.jeff.encryption.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/28.
 */
public final class FileIOUtils {

    public static byte[] readIn(File fromFile) throws FileNotFoundException {
        return readIn(new FileInputStream(fromFile));
    }

    public static byte[] readIn(FileInputStream fis) {
        try {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] readIn(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int offset;
        while ((offset = is.read(buffer)) != -1) {
            baos.write(buffer, 0, offset);
        }
        return baos.toByteArray();
    }

    public static void writeOut(File toFile, byte[] data) throws FileNotFoundException {
        writeOut(new FileOutputStream(toFile), data);
    }

    public static void writeOut(FileOutputStream fos, byte[] data) {
        try {
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
