/**
*
* @author Nikolaos.Kokkinos
* Martikel Nummer: 11052103
* Masterstudiengang Technische Informatik 
*/


package krypto.lab2.filesecurity.library.niko.kokkinos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class FileHelper {
	
	private final static int SALTLENGTH = 64;
	
	public static void writeEncryptedFile(String file, Cipher cipher, byte[] salt, Supplier<byte[]> plaintextSupplier){
		
		Optional<FileOutputStream> outFile = AESUtils.fileToWrite(s-> s + ".enc" , file);
		Optional<CipherOutputStream> cipherOut = outFile.map(f->new CipherOutputStream(f, cipher));
		
		byte[] bytesToWrite = plaintextSupplier.get();
		
		if(outFile.isPresent() && cipherOut.isPresent()){
			try(FileOutputStream os = outFile.get();
				CipherOutputStream cos = cipherOut.get();)
			{
				os.write(salt);
				final long before= System.currentTimeMillis();
				cos.write(bytesToWrite);
				final long after= System.currentTimeMillis();
				final long duration = after-before;
				System.out.println("Encrypted Bytes Written: " + bytesToWrite.length);
				System.out.println("Encryption duration " + duration + " milliseconds");
				cos.flush();
				
			}catch (IOException e) {
				System.out.println("Exception by writig encryption File: " + e.getMessage());}
		}
	}

	
	
	public static Optional<byte[]> readEncryptedFile(String file,
			Cipher cipher) {
		
		Optional<FileInputStream> inFile = AESUtils.fileToRead(s->s, file);
		Optional<CipherInputStream> cipherIn = inFile.map(f->new CipherInputStream(f, cipher));
		
		Optional<byte[]> message = Optional.empty(); 
				
		if(inFile.isPresent() && cipherIn.isPresent()){
			try(FileInputStream fi = inFile.get();
				CipherInputStream ci = cipherIn.get();)
				{
					int available = fi.available();
					
					long totalbytesSkipped = 0;
					long bytesSkipted = 0;
					while(totalbytesSkipped<SALTLENGTH){
						bytesSkipted = fi.skip(SALTLENGTH);
						totalbytesSkipped+=bytesSkipted;
					}
					
					message = Optional.of(new byte[available-SALTLENGTH]);
					int chunkBytes = 1;
					int totalBytesRead = 0;
					
					final long before= System.currentTimeMillis();
					while(totalBytesRead<message.get().length && chunkBytes>0){
						chunkBytes = ci.read(message.get());
						totalBytesRead+=chunkBytes;
					}
					
					final long after= System.currentTimeMillis();
					final long duration = after-before;
					System.out.println("Decryption duration " + duration + " milliseconds");
					System.out.println("Encrypted Bytes Read: " + totalBytesRead);
				}
			
			catch (IOException ioe){
				System.out.println("Exception by reading Encryption File: " + ioe.getMessage());
			}
		}
		
		return message;
	}
	
	public static void writeDecryptedFile(String file, final byte[] message){
		Optional<FileOutputStream> outFile = AESUtils.fileToWrite(s-> s + ".dec" , file);
		
		if(outFile.isPresent()){
			try(FileOutputStream os = outFile.get();){
				
				os.write(message);
				System.out.println("Decrypted Bytes Written: " + message.length);
			}catch(IOException e){
				System.out.println("Exception by writing Decryption File " + e.getMessage());
			}
		}
	}

}
