/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/


package krypto.lab2.filesecurity.library.niko.kokkinos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.NoSuchPaddingException;

public interface AESUtils {
	
	public static Function<String, Optional<SecretKeyFactory>> secretKeyfactory = (String algorithm) ->
	{
		Optional<SecretKeyFactory> secretKeyFactory;
		try {
			secretKeyFactory = Optional.of(SecretKeyFactory.getInstance(algorithm));
		} catch (NoSuchAlgorithmException e) {
			secretKeyFactory = Optional.empty();
		}
		return secretKeyFactory;
	};
			
	public static  BiFunction<SecretKeyFactory,PBEKeySpec, Optional<SecretKey>>  generateSecretKey = (skf,spec) -> {
				
		Optional<SecretKey> secretKey;
		try {
			secretKey = Optional.of(skf.generateSecret(spec));
		} catch (InvalidKeySpecException e) {
			secretKey = Optional.empty();
			System.out.println("Invalid Key Spec Exception: " + e.getMessage());
		}
													
		return secretKey;
	};
	
	public static PBEKeySpec createPBKeySpec(char[] password, byte[] salt, int iterations, int keyLength){
		return new PBEKeySpec(password, salt, iterations, keyLength);
	}
	
	public static Optional<byte[]> generatePbkeyBytes(Optional<SecretKeyFactory> kf,PBEKeySpec spec) {
		Optional<byte[]> pbkBytes = kf.flatMap(keyFactory-> AESUtils.generateSecretKey.apply(keyFactory, spec))
        							  .map((SecretKey key)->key.getEncoded());
		return pbkBytes;
	}
	
	public static Function<String,Optional<Cipher>> cipherInstance = (String transformation)->{
		Optional<Cipher> cipher;
		try{
			cipher = Optional.of(Cipher.getInstance(transformation));
			} catch (NoSuchAlgorithmException e) {
					cipher = Optional.empty();
			} catch (NoSuchPaddingException e) {
					cipher = Optional.empty();
					}
			return cipher;
		};	
		
   public static Optional<FileOutputStream> fileToWrite(UnaryOperator<String> fileNameOperation, String description){
	   String name = fileNameOperation.apply(description); 
	   Optional<FileOutputStream> file;
	   try {
			file = Optional.of(new FileOutputStream(name));
		} catch (FileNotFoundException e) {
			file = Optional.empty();
			System.out.println("File Not found, I will apply the default configuration");
		}
	   return file;   
   }
   
   public static Optional<FileInputStream> fileToRead(UnaryOperator<String> fileNameOperation, String description){
	   String name = fileNameOperation.apply(description); 
	   Optional<FileInputStream> file;
	   try {
			file = Optional.of(new FileInputStream(name));
		} catch (FileNotFoundException e) {
			file = Optional.empty();
			System.out.println("File Not found, I will apply the default configuration");
		}
	   return file;   
   }
   
   public static Optional<byte[]> copyOfRange(byte[] array, int from, int to){
	   
	   Optional<byte[]> result;
	   
	   try{
		   result = Optional.of(Arrays.copyOfRange(array, from, to));
	   }
	   
	   catch(ArrayIndexOutOfBoundsException obe){
		   result = Optional.empty();
	   }
	   catch(IllegalArgumentException ie){
		   result = Optional.empty();
	   }
	   catch(NullPointerException npe){
		   result = Optional.empty();
	   }
	   return result;				  
   }
   
   public static void initializeCipher(CipherInitializer initializer, 
		     						   int mode, SecretKeySpec secretKeySpec, IvParameterSpec iv) {
		
		try{
			initializer.initialize(mode, secretKeySpec, iv);
		} catch (InvalidKeyException e) {
			System.out.println("Invalid Key: " + e.getMessage());
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println("Invalid Algorithm parameter exception: " + e.getMessage());
		}
	}
   
   public static UnaryOperator<byte[]> saltBytes(Consumer<byte[]> consumer){
		return (bytes) -> {consumer.accept(bytes);
							return bytes;};
	}
   
   @FunctionalInterface
   public interface CipherInitializer{
		public void initialize(int mode, SecretKeySpec secretKeySpec, IvParameterSpec iv) throws InvalidKeyException, InvalidAlgorithmParameterException;

	}
   
}
