/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/



package krypto.lab2.encrytion.client.niko.kokkinos;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

import krypto.lab2.filesecurity.library.niko.kokkinos.AESFileSecurity;
import krypto.lab2.filesecurity.library.niko.kokkinos.DefaultsHelper;


public class EncryptionClient {
	
	public static void main(String[] args){
		AESFileSecurity aesFileSecurity = new AESFileSecurity();
		
		System.out.println("Encryption Client started....");
		
		Optional<String> answerFileLocation = readFromStdin("Where is the plain text file located? ").filter(s->s.length()>0).findFirst();
		
		Optional<String> answerFileName = readFromStdin("What is the name of the plain text file? Tipp: include file extensions if needed -> ").filter(s->s.length()>0).findFirst();
		
		Optional<String> password = readFromStdin("Type a password: ").filter(s->s.length()>0).findFirst();
	    
	    String fileLocationAndName = String.join("",answerFileLocation.orElse(""),answerFileName.orElse("example"));
	    	
	    
	    aesFileSecurity.encrypt(password.orElse(DefaultsHelper.hardCodedPassword).toCharArray(), fileLocationAndName, DefaultsHelper.readPlainTextFile(fileLocationAndName));
	    	 
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
