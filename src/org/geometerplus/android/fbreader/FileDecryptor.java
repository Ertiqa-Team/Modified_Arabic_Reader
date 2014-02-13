package org.geometerplus.android.fbreader;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

public class FileDecryptor {

	private static Cipher dcipher;
	private static FileInputStream inFile;
	private static FileOutputStream outFile;

	// 8-byte initialization vector
	byte[] msgNumber = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
			(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };

	public boolean init() {
		try {
			SecretKey key = new SecretKeySpec(msgNumber, "DES");
			IvParameterSpec zeroIv = new IvParameterSpec(msgNumber);

			dcipher = Cipher.getInstance("DES/CBC/PKCS7Padding", "BC");
			dcipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			return true;
		}

		catch (InvalidAlgorithmParameterException e) {
			Log.e("hayneee", "Invalid Alogorithm Parameter:" + e.getMessage());
			return false;
		} catch (NoSuchAlgorithmException e) {
			Log.e("hayneee", "No Such Algorithm:" + e.getMessage());
			return false;
		} catch (NoSuchPaddingException e) {
			Log.e("hayneee", "No Such Padding:" + e.getMessage());
			return false;
		} catch (InvalidKeyException e) {
			Log.e("hayneee", "Invalid Key:" + e.getMessage());
			return false;
		} catch (Exception e) {
			Log.e("hayneee", "Exception:" + e.getMessage());
			return false;
		}
	}

	public boolean decrypt(InputStream is, OutputStream os) {

		try {
			final int BUFFER = 2048;
			// bytes read from stream will be decrypted
			CipherInputStream cis = new CipherInputStream(is, dcipher);
			// Buffer the ouput to the file
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					os, BUFFER);
			// Write the contents
			int count = 0;
			byte[] data = new byte[BUFFER];
			while ((count = cis.read(data, 0, BUFFER)) != -1) {
				bufferedOutputStream.write(data, 0, count);
			}

			// Flush and close the buffers

			bufferedOutputStream.flush();
			bufferedOutputStream.close();

			cis.close();
			is.close();
			os.close();

			return true;
		} catch (IOException e) {
			Log.e("hayneee", "copyFiles I/O Error:" + e.getMessage());
			return false;
		} catch (Exception e) {
			Log.e("hayneee", "copyFiles Exception:" + e.getMessage());
			return false;
		}

	}
	
	
	public boolean decrypt(String filename,String encpath,String decpath) {
		try {
			inFile = new FileInputStream(encpath+filename);
			outFile =   new FileOutputStream(decpath+filename);
			final int BUFFER = 2048;

			// bytes read from stream will be decrypted
			CipherInputStream cis = new CipherInputStream(inFile, dcipher);

			// Buffer the ouput to the file
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					outFile, BUFFER);

			// Write the contents
			int count = 0;
			byte[] data = new byte[BUFFER];
			while ((count = cis.read(data, 0, BUFFER)) != -1) {
				bufferedOutputStream.write(data, 0, count);
			}

			// Flush and close the buffers

			bufferedOutputStream.flush();
			bufferedOutputStream.close();

			cis.close();
			inFile.close();
			outFile.close();

			return true;
		} catch (IOException e) {
			Log.e("hayneee", "copyFiles I/O Error:" + e.getMessage());
			return false;
		} catch (Exception e) {
			Log.e("hayneee", "copyFiles Exception:" + e.getMessage());
			return false;
		}
	}
}