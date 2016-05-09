/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/


package krypto.lab2.filesecurity.library.niko.kokkinos;

import java.util.Optional;
import java.util.function.Supplier;

import javax.crypto.Cipher;
import javax.crypto.spec.PBEKeySpec;

public class AESFileSecurity {
	
	private final static int ITERATIONS = 2048;
	private final static int KEYLENGTH = 256;
	private final static int SALTLENGTH = 64;
	
	public void encrypt(char[] password, String file, Supplier<byte[]> plaintextSupplier){
	
		final byte[] salt = AESUtils.saltBytes(SaltHelper.fromSecureRandom).apply(new byte[SALTLENGTH]);
		
        
        PBEKeySpec spec = AESUtils.createPBKeySpec(password, salt, ITERATIONS, KEYLENGTH);
        
		Optional<Cipher> cipher = CipherHelper.tryToInitializeCipher(spec, Cipher.ENCRYPT_MODE);
		
		cipher.ifPresent((Cipher c)->FileHelper.writeEncryptedFile(file, 
					   cipher.get(), 
					   salt,
					   plaintextSupplier));	
	}

	public void decrypt(char[] password, String file){
				
		byte[] salt = AESUtils.saltBytes(SaltHelper.fromFile(file)).apply(new byte[SALTLENGTH]);
		
        PBEKeySpec spec = AESUtils.createPBKeySpec(password, salt, ITERATIONS, KEYLENGTH);
        
		Optional<Cipher> cipher = CipherHelper.tryToInitializeCipher(spec, Cipher.DECRYPT_MODE);
	
		cipher.ifPresent((Cipher c)->FileHelper.readEncryptedFile(file, c)
				                                       .ifPresent(b->FileHelper.writeDecryptedFile(file, b)));
		
	}
}
