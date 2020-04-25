package com.mwj.personweb.ASE;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSACoder {

  public static final String KEY_ALGORITHM = "RSA";

  private static final int KEY_SIZE = 512;

  private static final String PUBLIC_KEY = "RSAPublicKey";

  private static final String PRIVATE_KEY = "RSAPrivateKey";

  private static String strings = "菜,大米,猪肉,蒸";

  public static Map<String, Object> initKey() throws Exception {

    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);

    keyPairGenerator.initialize(KEY_SIZE);

    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

    Map<String, Object> keyMap = new HashMap<String, Object>();
    keyMap.put(PUBLIC_KEY, publicKey);
    keyMap.put(PRIVATE_KEY, privateKey);
    return keyMap;
  }

  public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {

    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

    PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
    return cipher.doFinal(data);
  }

  public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {

    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);

    PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(Cipher.ENCRYPT_MODE, pubKey);
    return cipher.doFinal(data);
  }

  public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {

    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

    PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    return cipher.doFinal(data);
  }

  public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {

    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
    PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(Cipher.DECRYPT_MODE, pubKey);
    return cipher.doFinal(data);
  }

  public static byte[] getPrivateKey(Map<String, Object> keyMap) {
    Key key = (Key) keyMap.get(PRIVATE_KEY);
    return key.getEncoded();
  }

  public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
    Key key = (Key) keyMap.get(PUBLIC_KEY);
    return key.getEncoded();
  }

  public static void main(String[] args) throws Exception {
    String str = strings;
    String ds =
        "juiefj fqdkfjfhjf dfejiofnsfjwiojuefhhfwfeeffjfjeirufjzljfjiumejieurtnjiejfjaqdjxwdjj jeijejels ejieiurfjslsjf jefdiufjduse jkhfjyu jfeija;jdfujeqw jfdjfkdjvhfjfsjefjwqeifjdjfjfiea3fdjjfhjfjiennkuiujdjkdjwejwdfsejojdujhg fjfjfjfj"
            + "fosajfsjfwjfsur9ef"
            + "hfsfowjrws]sfjhoejfj]"
            + "fjeufqsufemfsqejfmpi"
            + "dhjaida";
    Map<String, Object> keyMap = RSACoder.initKey();
    byte[] publicKey = RSACoder.getPublicKey(keyMap);
    byte[] privateKey = RSACoder.getPrivateKey(keyMap);
    byte[] code1 = RSACoder.encryptByPrivateKey(str.getBytes(), privateKey);
    byte[] decode1 = RSACoder.decryptByPublicKey(code1, publicKey);
    System.out.println("解密数据" + new String(decode1));
  }
}
