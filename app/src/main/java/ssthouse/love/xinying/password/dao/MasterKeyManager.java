package ssthouse.love.xinying.password.dao;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ssthouse on 26/03/2017.
 */

public class MasterKeyManager {

    private Context mContext;

    private static final String PREFERENCE_NAME = "master_password";
    private static final String KEY_MASTER_PASSWORD = "masterPassword";
    private static final String DEFAULT_MASTER_PASSWORD = "ssthouse";

    public MasterKeyManager(Context mContext) {
        this.mContext = mContext;
    }

    public String getMasterPassword() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String masterPassword = sharedPreferences.getString(KEY_MASTER_PASSWORD, DEFAULT_MASTER_PASSWORD);
        if (masterPassword.equals(DEFAULT_MASTER_PASSWORD)) {
            return md5(DEFAULT_MASTER_PASSWORD);
        } else {
            return masterPassword;
        }
    }

    public void setMasterPassword(String masterPassword) {
        String md5Password = md5(masterPassword);
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_MASTER_PASSWORD, md5Password);
        editor.apply();
    }

    public static String md5(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
