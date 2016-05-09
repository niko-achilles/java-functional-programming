/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/


package krypto.lab2.frequency.niko.kokkinos;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

import krypto.lab2.filesecurity.library.niko.kokkinos.FrequencyHelper;



public class FrequencyClient {
	
	
	public static void main(String[] args) throws Exception{
		
		System.out.println("Frequency Client started....");
		
		Optional<String> answerFileLocation = readFromStdin("Where is the encrypted file located? ").filter(s->s.length()>0).findFirst();
		
		Optional<String> answerFileName = readFromStdin("What is the name of the encrypted file? Tipp: include file extensions if needed -> ").filter(s->s.length()>0).findFirst();
		
	    String fileLocationAndName = String.join("",answerFileLocation.orElseThrow(()->new Exception("You have to provide the file location")),
	    									answerFileName.orElseThrow(()->new Exception("You have to provide the file name")));
	    	
	    start(fileLocationAndName);
	    	 
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
	
	private static void start(String file){
	
		FrequencyHelper.readAndMeasureEncryptedFile(file);	
	}

}
