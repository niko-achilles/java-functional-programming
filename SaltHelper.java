/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/


package krypto.lab2.filesecurity.library.niko.kokkinos;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.function.Consumer;

public class SaltHelper {
	
	public static Consumer<byte[]> fromSecureRandom = (bytes)-> {
		SecureRandom r = new SecureRandom();
		r.nextBytes(bytes);
	}; 

	public static Consumer<byte[]> fromFile(String file){
		return (bytes)->{
						Optional<FileInputStream> inFile = AESUtils.fileToRead(s-> s, file);
						
						if (inFile.isPresent()){
							try(FileInputStream fis = inFile.get();){
								fis.read(bytes);
								}
							catch(IOException ioe){
								System.out.println("Exception by reading salt from file: " + ioe.getMessage());
								}
							}
						};
	}

}
