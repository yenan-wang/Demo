package com.ngb.common.utils;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
    public static final String MODE_AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
    public static final String MODE_AES_CTR_PKCS5PADDING = "AES/CTR/PKCS5Padding";
    public static final String MODE_AES_CTR_NOPADDING = "AES/CTR/NoPadding";
    private static final String TAG = "EncryptUtil";
    private static final String IV_CONTENT = "nlLoNPE5Tv2CfMd+vVkI/A==";
    private static final String ALGORITHM_RSA = "RSA";
    private static final String ALGORITHM_AES = "AES";
    private static final String ALGORITHM_MD5 = "MD5";
    private static final String ALGORITHM_DES = "DES";
    private static final String CHARACTER = "UTF-8";

    public static byte[] encryptAES2Base64(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv) {
        return base64Encode(encryptAES(data, key, transformation, iv));
    }

    public static byte[] decryptBase64AES(final byte[] data,
                                          final byte[] key,
                                          final String transformation,
                                          final byte[] iv) {
        return decryptAES(base64Decode(data), key, transformation, iv);
    }

    public static byte[] encryptAES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, ALGORITHM_AES, transformation, iv, true);
    }

    public static byte[] decryptAES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, ALGORITHM_AES, transformation, iv, false);
    }

    private static byte[] symmetricTemplate(final byte[] data,
                                            final byte[] key,
                                            final String algorithm,
                                            final String transformation,
                                            final byte[] iv,
                                            final boolean isEncrypt) {
        if ((data == null) || (data.length == 0) || (key == null) || (key.length == 0)) {
            return null;
        }
        if ((iv != null) && (iv.length > 16)) {
            return null;
        }
        SecretKey secretKey = null;
        try {
            if (ALGORITHM_DES.equals(algorithm)) {
                //InvalidKeyException
                DESKeySpec desKeySpec = new DESKeySpec(key);
                //NoSuchAlgorithmException
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
                //InvalidKeySpecException
                secretKey = keyFactory.generateSecret(desKeySpec);
            } else {
                secretKey = new SecretKeySpec(key, algorithm);
            }
            //NoSuchPaddingException
            Cipher cipher = Cipher.getInstance(transformation);
            int opMode = isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            if ((iv == null) || (iv.length == 0)) {
                cipher.init(opMode, secretKey);
            } else {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                // InvalidAlgorithmParameterException
                cipher.init(opMode, secretKey, params);
            }
            //BadPaddingException | IllegalBlockSizeException
            return cipher.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
            LogUtil.e(TAG, "error: " + e.getMessage());
            return null;
        }
    }

    public static byte[] base64Encode(final byte[] input) {
        if (input != null) {
            return Base64.encode(input, Base64.NO_WRAP);
        }
        return null;
    }

    public static byte[] base64Decode(final byte[] input) {
        if (input != null) {
            return Base64.decode(input, Base64.NO_WRAP);
        }
        return null;
    }

    public static byte[] getIv() {
        return Base64.decode(IV_CONTENT, Base64.NO_WRAP);
    }
}
