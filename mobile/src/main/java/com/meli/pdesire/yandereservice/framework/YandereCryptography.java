package com.meli.pdesire.yandereservice.framework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Created by PDesire on 8/29/17.
 */

@SuppressLint("Registered")

public class YandereCryptography extends Activity {
    @NonNull public static String fileToMD5(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0)
                    digest.update(buffer, 0, numRead);
            }
            byte [] md5Bytes = digest.digest();
            return convertHashToString(md5Bytes);
        } catch (Exception e) {
            return e.toString();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ignored) { }
            }
        }
    }

    @NonNull private static String convertHashToString(byte[] md5Bytes) {
        StringBuilder returnVal = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            returnVal.append(Integer.toString((md5Byte & 0xff) + 0x100, 16).substring(1));
        }
        return returnVal.toString().toUpperCase();
    }

    @NonNull public String convertStringToSHA256 (String string) {
        return Hashing.sha256().hashString( string, Charsets.UTF_8 ).toString();
    }

    @NonNull public String convertStringToSHA256 (int string) {
        String finalString = getString(string);
        return Hashing.sha256().hashString(finalString, Charsets.UTF_8 ).toString();
    }
}
