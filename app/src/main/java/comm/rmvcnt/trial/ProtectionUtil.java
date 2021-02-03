package comm.rmvcnt.trial;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ProtectionUtil {

    private static Context context;
    private static ProtectionUtil instance;


    public static void init(Context context){
        instance = new ProtectionUtil();
        ProtectionUtil.context = context;
    }

    public ProtectionUtil getInstance(){
        if (instance==null){
            throw new RuntimeException("Please initialize first by calling init()");
        }
        return instance;
    }


    public byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }


    public byte[] encrypt(byte[] plaintext, byte[] key, byte[] IV) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }


    public String decrypt(byte[] cipherText, SecretKey key, byte[] IV)
    {
        try {
            Log.e("size",key.getEncoded().length+"");
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public  String decrypt(String cipherText, String key, String IV)
    {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(decoderfun(key), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(decoderfun(IV));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedText = cipher.doFinal(decoderfun(cipherText));
            return new String(decryptedText);
        } catch (Exception e) {
            Log.e("value",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public String encoderfun(byte[] decval) {
        String conVal= Base64.encodeToString(decval,Base64.DEFAULT);
        return conVal;
    }

    public byte[] decoderfun(String enval) {
        byte[] conVal = Base64.decode(enval,Base64.DEFAULT);
        return conVal;

    }

}
