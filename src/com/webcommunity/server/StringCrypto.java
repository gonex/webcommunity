package com.webcommunity.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class StringCrypto {
	
	private static final Logger log = Logger.getLogger(StringCrypto.class.getName());
	
	private static Object LOCK = new Object();
	private static String KEY = "PrivateKey";


	private StringCrypto() {
	}
	
	public static String encrypt(String value) {
		try {
			DESKeySpec keySpec = new DESKeySpec(KEY.getBytes("UTF8")); 
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secret = keyFactory.generateSecret(keySpec);
			
			byte[] bytes = value.getBytes("UTF8");      
			synchronized(LOCK) {
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.ENCRYPT_MODE, secret);
				String result = Base64Coder.encodeLines(cipher.doFinal(bytes));
				return result;
			}
		} catch (Exception ex) {
			log.log(Level.SEVERE, "error encrypting string", ex);
		}
		
		return null;
	}
	
	public static String decrypt(String value) {
		try {
			DESKeySpec keySpec = new DESKeySpec(KEY.getBytes("UTF8")); 
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secret = keyFactory.generateSecret(keySpec);

			byte[] bytes = Base64Coder.decodeLines(value);
			synchronized (LOCK) {
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.DECRYPT_MODE, secret);
				String result = new String(cipher.doFinal(bytes), "UTF8");
				return result;
			}
		} catch (Exception ex) {
			log.log(Level.SEVERE, "error decrypting string", ex);
		}
		
		return null;
	}
}
