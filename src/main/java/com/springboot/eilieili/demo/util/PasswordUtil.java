package com.springboot.eilieili.demo.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * Created by Intellij IDEA.
 * User:  qq695
 * Date:  2020/2/15
 */
public class PasswordUtil {
    private static final String EncryptAlg = "AES";

    private static final String Cipher_Mode = "AES/ECB/PKCS7Padding";

    private static final String Encode = "UTF-8";

    private static final int Secret_Key_Size = 32;

    private static final String Key_Encode = "UTF-8";

    private static final String SECRET_KEY = "EILIEILI_KEY";

    /**
     * AES/ECB/PKCS7Padding 加密
     *
     * @param content //     * @param key     密钥
     * @return aes加密后 转base64
     * @throws Exception
     */
    public static String Encrypt(String content) throws Exception {
        try {
            Security.addProvider(new BouncyCastleProvider());

            Cipher cipher = Cipher.getInstance(Cipher_Mode);
            byte[] realKey = getSecretKey();
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(realKey, EncryptAlg));
            byte[] data = cipher.doFinal(content.getBytes(Encode));
            String result = new Base64().encodeToString(data);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("AES加密失败：content=" + content + " key=" + SECRET_KEY);
        }
    }

    /**
     * AES/ECB/PKCS7Padding 解密
     *
     * @param content //     * @param key     密钥
     * @return 先转base64 再解密
     * @throws Exception
     */
    public static String Decrypt(String content) throws Exception {
        try {
            Security.addProvider(new BouncyCastleProvider());

            byte[] decodeBytes = Base64.decodeBase64(content);

            Cipher cipher = Cipher.getInstance(Cipher_Mode);
            byte[] realKey = getSecretKey();
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(realKey, EncryptAlg));
            byte[] realBytes = cipher.doFinal(decodeBytes);

            return new String(realBytes, Encode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("AES解密失败：Aescontent = " + e.fillInStackTrace(), e);
        }
    }

    /**
     * 对密钥key进行处理：如密钥长度不够位数的则 以指定paddingChar 进行填充；
     * 此处用空格字符填充，也可以 0 填充，具体可根据实际项目需求做变更
     *
     * @return
     * @throws Exception
     */
    private static byte[] getSecretKey() throws Exception {
        final byte paddingChar = ' ';

        byte[] realKey = new byte[Secret_Key_Size];
        byte[] byteKey = SECRET_KEY.getBytes(Key_Encode);
        for (int i = 0; i < realKey.length; i++) {
            if (i < byteKey.length) {
                realKey[i] = byteKey[i];
            } else {
                realKey[i] = paddingChar;
            }
        }

        return realKey;
    }


}
