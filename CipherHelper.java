/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/


package krypto.lab2.filesecurity.library.niko.kokkinos;

import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class CipherHelper {
	
	private final static String AESCTRPKCS5Padding = "AES/CTR/PKCS5Padding";
	private static final String AESALGORITHM = "AES";
	private final static String ALGORITHMPBKDF2WithHmacSHA1 = "PBKDF2WithHmacSHA1";
	
	public static Optional<Cipher> tryToInitializeCipher(PBEKeySpec spec, int mode){
		
		Optional<Cipher> cipher = Optional.empty();
		
		Optional<SecretKeyFactory> kf = AESUtils.secretKeyfactory.apply(ALGORITHMPBKDF2WithHmacSHA1);
		
		Optional<byte[]> pbkBytes = AESUtils.generatePbkeyBytes(kf, spec);
		
		Optional<SecretKeySpec> secretKeySpec = pbkBytes.map((byte[] bytes)->
																			AESUtils.copyOfRange(bytes, 0, (bytes.length)/2))
														.map(k->new SecretKeySpec(k.get(), AESALGORITHM));
		
		Optional<IvParameterSpec> iv = pbkBytes.map((byte[] bytes)->
																	AESUtils.copyOfRange(bytes, 16, bytes.length))
											   .map(c->new IvParameterSpec(c.get()));
		
		cipher = AESUtils.cipherInstance.apply(AESCTRPKCS5Padding);
		
		if(cipher.isPresent() && secretKeySpec.isPresent() && iv.isPresent()){
			AESUtils.initializeCipher(cipher.get()::init, 
					  mode, 
					  secretKeySpec.get(), 
					  iv.get());
		}
		
		return cipher;
	}

}
