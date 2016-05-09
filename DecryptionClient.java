/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/



package krypto.lab2.decryption.client.niko.kokkinos;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

import krypto.lab2.filesecurity.library.niko.kokkinos.AESFileSecurity;
import krypto.lab2.filesecurity.library.niko.kokkinos.DefaultsHelper;


public class DecryptionClient {
	
	
	public static void main(String[] args){
		AESFileSecurity aesFileSecurity = new AESFileSecurity();
		
		System.out.println("Decryption Client started....");
		
		Optional<String> answerFileLocation = readFromStdin("Where is the encrypted file located? ").filter(s->s.length()>0).findFirst();
		
		Optional<String> answerFileName = readFromStdin("What is the name of the encrypted file? Tipp: include file extensions if needed -> ").filter(s->s.length()>0).findFirst();
		
		Optional<String> password = readFromStdin("Type a password: ").filter(s->s.length()>0).findFirst();
	    
	    String fileLocationAndName = String.join("",answerFileLocation.orElse("./../EncryptAESProject/"),answerFileName.orElse("example.enc"));
	    	
	    aesFileSecurity.decrypt(password.orElse(DefaultsHelper.hardCodedPassword).toCharArray(), fileLocationAndName);
	    	 
	    System.out.println("Bye bye");
	}
	
	private static Stream<String> readFromStdin(String prompt) {
		System.out.print(prompt);
		
	    Scanner scanner = new Scanner(System.in);
	    Supplier<String> stdin = () -> {
	      
	      return scanner.nextLine();
	    };
	    return Stream.of(stdin.get());
	  }
	

	

}

