#Cryprography Lab

In this lab, files are encrypted with the AES block cipher in the Counter mode. The Java Cryptography Architecture (JCA) is used for file encryption. 

You may use Eclipse as Java IDE. 
Preparation: http://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html

Special emphasis should be put on the introduction, the description of the Cipher class and the CipherInputStream class.

Implement a class AESFileSecurity (without main method) which encrypts and decrypts files with the AES cipher. Write methods encrypt(char[] password, File file) and decrypt(char[] password, File file) which encrypts resp. decrypts a file with the AES algorithm in the CTR mode using the given password.

First, create a byte array salt with 64 random bytes using the SecureRandom class and the nextBytes method. Then derive 256 key bits (32 bytes) from the password and the salt. Use the standardized password-based key derivation function PBKDF2 with 2048 iterations of HMAC-SHA1:

    SecretKeyFactory kf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); 
    SecretKey pbkey = kf.generateSecret(specs); 
    byte[] pbkeyBytes = pbkey.getEncoded();

Copy the first 16 bytes of pbkeyBytes to the byte array key and the other 16 bytes to the byte array counter. Create an IvParameterSpec object using counter. 
Then get an instance of a Cipher object with the transformation "AES/CTR/PKCS5Padding". 
Construct a SecretKeySpec object using key and the algorithm name "AES". 
Then initialize the Cipher object with the cipher mode (encryption or decryption), the SecretKeySpec object and the IvParameterSpec object. 
Create a FileInputStream (using the given file), a CipherInputStream (using the FileInputStream and Cipher objects) and a FileOutputStream. 
The output filename shall be derived from the original filename by concatenating with .enc resp. .dec. 
The data is written to the FileOutputStream using the write method. 

For encryption, first write the 64 salt bytes to the output stream. Then read chunks of data from the CipherInputStream (i.e. encrypted data) and write them to the output stream. 

For decryption, read the salt bytes from the FileInputStream and derive a SecretKeySpec, an IvParameterSpec and a Cipher object. Then read blocks of data from the CipherInputStream (i.e. decrypted data) and write them to the output stream. Close all streams if the data is completely processed. You need to import several packages and classes. Various possible exceptions must be caught.

Create executable applications EncryptAES and DecryptAES which create an AESFileSecurity object and invoke the encrypt resp. decrypt methods using some static test file and password.

Implement additional choosePassword and chooseFile methods in your AESFileSecurity class.
Extend the EncryptAES and DecryptAES classes and let the user choose a password and a file before encryption resp. decryption.

Integrate timers and measure the throughput of Java AES encryption and decryption (without the user interaction) in CTR mode. Find an optimal byte array size for reading and writing streams.

Hints: A 100-megabyte test le can be generated with dd if=/dev/urandom of=largefile.bin bs=1024 count=102400 . System.currentTimeMillis() gives a timestamp which can be used for your measurement.
1.Encrypt a 10-megabyte all-zero plaintext and analyze the randomness of the AES ciphertext by counting the frequency of the bytes. Let N be the byte-length of the ciphertext (without the 64 salt bytes). Determine how often each byte value i = 0,1...255 occurs and call this number Ni. Theexpectation value is E = N/256 . 

Print out the occurrences Ni, compute the x^2 test statistic and compare your result with the x^2 distribution function (with 255 degrees of freedom) which is plotted below. x^2 can be used as a test for fit of a uniform distribution.

Hints: A 10-megabyte zero file can be generated with dd if=/dev/zero of=zerofile.bin bs=1024 count=10240 . Write a Frequency class which opens an encrypted le, creates a FileInputStream object infile, reads and ignores the 64 salt bytes and then recursively reads a single byte with i = infile.read(), where i is an integer. Then increment N[i] and read the next byte until i = -1.


