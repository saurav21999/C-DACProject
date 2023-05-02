package com.onlineshopping.security;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import javax.crypto.*;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;


public class PasswordEcnoder {

	//Declartion of varieable
		private static final Random random =new SecureRandom();
		private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		private static final int itrations=10000;
		private static final int keylength=256;
		


		
		//method to genrate the hash value
		public static byte[] hash(char[] password,byte[] salt)
		{
			PBEKeySpec spec=new PBEKeySpec(password,salt,itrations,keylength);
			Arrays.fill(password, Character.MIN_VALUE);
			try
			{
				SecretKeyFactory skf=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
				return skf.generateSecret(spec).getEncoded();
			}
			catch(NoSuchAlgorithmException | InvalidKeySpecException e)
			{
				throw new AssertionError("Error while Hashing password: "+e.getMessage(),e);
			}
			finally
			{
				spec.clearPassword();
			}
		}
		
		public static String genrateSecurePassword(String password,String salt)
		{
			System.out.println("in genrate secure password");
			String finalval=null;
			
			byte[] securePassword=hash(password.toCharArray(),salt.getBytes());
			
			finalval=Base64.getEncoder().encodeToString(securePassword);
			System.out.println(finalval);
			return finalval;
			
		}
	
	
	
	
}
