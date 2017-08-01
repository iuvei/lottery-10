package com.wintv.framework.utils;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import com.wintv.framework.tags.Message;



import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesEncrypt {
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	public static final String DES_ENCRYPTION_SCHEME = "DES";
	private static String ENCRYPTION_SCHEME = "DESede";
	private static String DEFAULT_ENCRYPTION_KEY = "This is a fairly long phrase used to encrypt";
	private KeySpec				keySpec;
	private SecretKeyFactory	keyFactory;
	private Cipher				cipher;
	private static DesEncrypt desede;
	private static DesEncrypt des;
	private static final String	UNICODE_FORMAT = "UTF8";
	
	private static DesEncrypt getDesEdeInstance()throws Exception{
		if(desede == null){
			ENCRYPTION_SCHEME = DESEDE_ENCRYPTION_SCHEME;
			desede = new DesEncrypt();
		}
		return desede;
	}
	private static DesEncrypt getDesInstance()throws Exception{
		if(des == null){
			ENCRYPTION_SCHEME = DES_ENCRYPTION_SCHEME;
			des = new DesEncrypt();
		}
		return des;
	}
	/**
	 * get encrypt string
	 * @param encrypt
	 * @return
	 * @throws Exception
	 */
	public static String desEdeEncrypt(String encrypt)throws Exception{
		return getDesEdeInstance().encrypt(encrypt);
	}
	
	/**
	 * get decrypt string
	 * @param dncrypt
	 * @return
	 * @throws Exception
	 */
	public static String desEdeDecrypt(String dncrypt)throws Exception{
		return getDesEdeInstance().decrypt(dncrypt);
	}
	
	static{
		DEFAULT_ENCRYPTION_KEY = Message.getMsg("ENCRYPT_KEY", false);
	}
	
	public DesEncrypt()throws EncryptionException{
		if ( DEFAULT_ENCRYPTION_KEY == null )
			throw new IllegalArgumentException( "encryption key was null" );
		else if ( DEFAULT_ENCRYPTION_KEY.trim().length() == 0 )
				throw new IllegalArgumentException("encryption key was less than 24 characters" );
	
		try
		{
			byte[] keyAsBytes = DEFAULT_ENCRYPTION_KEY.getBytes( UNICODE_FORMAT );
	
			if ( ENCRYPTION_SCHEME.equals( DESEDE_ENCRYPTION_SCHEME) )
			{
				keySpec = new DESedeKeySpec( keyAsBytes );
			}
			else if ( ENCRYPTION_SCHEME.equals( DES_ENCRYPTION_SCHEME ) )
			{
				keySpec = new DESKeySpec( keyAsBytes );
			}
			else
			{
				throw new IllegalArgumentException( "Encryption scheme not supported: "
													+ ENCRYPTION_SCHEME );
			}
	
			keyFactory = SecretKeyFactory.getInstance( ENCRYPTION_SCHEME );
			cipher = Cipher.getInstance( ENCRYPTION_SCHEME );
	
		}
		catch (InvalidKeyException e)
		{
			throw new EncryptionException( e );
		}
		catch (UnsupportedEncodingException e)
		{
			throw new EncryptionException( e );
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new EncryptionException( e );
		}
		catch (NoSuchPaddingException e)
		{
			throw new EncryptionException( e );
		}
	}

	public String encrypt( String unencryptedString ) throws EncryptionException
	{
		if ( unencryptedString == null || unencryptedString.trim().length() == 0 )
				throw new IllegalArgumentException(
						"unencrypted string was null or empty" );

		try
		{
			SecretKey key = keyFactory.generateSecret( keySpec );
			cipher.init( Cipher.ENCRYPT_MODE, key );
			byte[] cleartext = unencryptedString.getBytes( UNICODE_FORMAT );
			byte[] ciphertext = cipher.doFinal( cleartext );

			BASE64Encoder base64encoder = new BASE64Encoder();
			return base64encoder.encode( ciphertext );
		}
		catch (Exception e)
		{
			throw new EncryptionException( e );
		}
	}

	public String decrypt( String encryptedString ) throws EncryptionException
	{
		if ( encryptedString == null || encryptedString.trim().length() <= 0 )
				throw new IllegalArgumentException( "encrypted string was null or empty" );

		try
		{
			SecretKey key = keyFactory.generateSecret( keySpec );
			cipher.init( Cipher.DECRYPT_MODE, key );
			BASE64Decoder base64decoder = new BASE64Decoder();
			byte[] cleartext = base64decoder.decodeBuffer( encryptedString );
			byte[] ciphertext = cipher.doFinal( cleartext );

			return bytes2String( ciphertext );
		}
		catch (Exception e)
		{
			throw new EncryptionException( e );
		}
	}

	private static String bytes2String( byte[] bytes )
	{
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++)
		{
			stringBuffer.append( (char) bytes[i] );
		}
		return stringBuffer.toString();
	}

	@SuppressWarnings("serial")
	public static class EncryptionException extends Exception
	{
		public EncryptionException( Throwable t )
		{
			super(t);
		}
	}
	
	public static void main(String []args)throws Exception{
		//System.out.println(DesEncrypt.getDesEdeInstance().encrypt("72"));
		System.out.println(DesEncrypt.desEdeEncrypt("72"));
		System.out.println(DesEncrypt.desEdeDecrypt("+p3eruCSwik="));
		System.out.println(DesEncrypt.getDesEdeInstance().decrypt("+p3eruCSwik="));
	}
}