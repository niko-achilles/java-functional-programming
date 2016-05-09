/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/


package krypto.lab2.filesecurity.library.niko.kokkinos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FrequencyHelper {
	private final static int SALTLENGTH = 64;
	
	public static void readAndMeasureEncryptedFile(String file){
		
	
		Optional<FileInputStream> inFile = AESUtils.fileToRead(s->s, file);
		 
		if(inFile.isPresent()){
			try(FileInputStream fi = inFile.get();)
				{
				int available = fi.available();
				
				long totalbytesSkipped = 0;
				long bytesSkipted = 0;
				while(totalbytesSkipped<SALTLENGTH){
					bytesSkipted = fi.skip(SALTLENGTH);
					totalbytesSkipped+=bytesSkipted;
				}
				
				System.out.println("calculating...frequency of bytes");
				
				Integer[] arrayOfIntegers = Stream.generate(
						()->{
							Optional<Integer> result= Optional.empty();
							try
							{
								result = Optional.of(fi.read());
							}catch(IOException ioe){}
						return result;
						})
						.limit(available-SALTLENGTH).map(i->i.get().intValue())
						.toArray(Integer[]::new);

				Map<Integer, Long> counted = Arrays.stream(arrayOfIntegers).unordered().parallel()
									.collect(Collectors.groupingBy(o -> o, Collectors.counting()));
				
				System.out.println("Statistics" + counted);
				
				double E = arrayOfIntegers.length / 256.0;
				double raiseTo = 2.0;
				System.out.println("E: " + E);
				
				double distribution = counted.values()
										.stream()
										.mapToDouble(
														l->
															(Math.pow(l.doubleValue()-E, raiseTo))/E)
															.sum();
				System.out.println("x Distribution: " + distribution);
				
				}catch (IOException ioe){
				System.out.println("Exception by reading and measuring frequency of encryption file: " + ioe.getMessage());
				}
			}
	}
}
