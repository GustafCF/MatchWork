package com.br.MatchWork.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RsaKeyGenerator {

    private static final Logger logger = Logger.getLogger(RsaKeyGenerator.class.getName()); 

    private static final String PUBLIC_KEY_FILE = "public.key";
    private static final String PRIVATE_KEY_FILE = "private.key";

    private final RSAPublicKey publickey;
    private final RSAPrivateKey privatekey;

    public RsaKeyGenerator() {
        File publickeyFile = new File("PUBLIC_KEY_FILE");
        File privatekeyFile = new File("PRIVATE_KEY_FILE");

        if (publickeyFile.exists() && privatekeyFile.exists()) {
            this.publickey = loadPublicKey(publickeyFile);
            this.privatekey = loadPrivateKey(privatekeyFile);
            logger.log(Level.INFO, "public key loaded sucessfully!");
            logger.log(Level.INFO, "private key loaded sucessfully!");
        } else {
            KeyPair keyPair = generateRsaKey();
            this.publickey = (RSAPublicKey) keyPair.getPublic();
            this.privatekey = (RSAPrivateKey) keyPair.getPrivate();
            saveKeyToFile(PUBLIC_KEY_FILE, this.publickey.getEncoded());
            saveKeyToFile(PRIVATE_KEY_FILE, this.privatekey.getEncoded());

        }
    }

    private KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error generating RSA key pair!");
            throw new RuntimeException("Error generating RSA key pair!");
        }
    }

    private void saveKeyToFile(String filename, byte[] key) {
        try(FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(key);
        } catch(IOException e) {
            logger.log(Level.SEVERE, "Error saving key to file!");
            throw new RuntimeException("Error saving key to file!"); 
        }
    }

    private RSAPublicKey loadPublicKey(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] keyBytes = fis.readAllBytes();
            return (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error loading public key");
            throw new RuntimeException("Error Loading public key");
        }
    }

    private RSAPrivateKey loadPrivateKey(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] keyBytes = fis.readAllBytes();
            return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error Loading private key!");
            throw new RuntimeException("Error Loading private key");
        }
    }

    public RSAPublicKey getPublic() {
        return publickey;
    }

    public RSAPrivateKey getPrivate() {
        return privatekey;
    }
}