/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/


package krypto.lab2.filesecurity.library.niko.kokkinos;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.function.Supplier;

public class DefaultsHelper {
	
	public static String hardCodedPassword =  "3Xtr0s3cr3t4Ju";
	
	public static Supplier<byte[]> messageSupplier = ()-> "crypto aes is fun".getBytes(Charset.forName("UTF-8"));

	public static Supplier<byte[]> readPlainTextFile(String file) {
		 
		return ()->{Optional<FileInputStream> inFile = AESUtils.fileToRead(s->s, file);
		
		Optional<byte[]> message = Optional.empty();
		
		if(inFile.isPresent()){
			try(FileInputStream fi = inFile.get();)
				{
					int available = inFile.get().available();
					
					message = Optional.of(new byte[available]);
					fi.read(message.get());
				}
			
			catch (IOException ioe){
				System.out.println(ioe.getMessage());
			}
		}
		
		return message.orElseGet(messageSupplier);
		};
	}
	
}
