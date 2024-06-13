package com.example.filenamecoder.rsa;

import android.util.Log;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class RSA {

    private BigInteger e;
    private BigInteger d;
    private BigInteger n;

    public RSA(int bitLength) {
        createKeys(bitLength);
    }

    private void createKeys(int bitLength) {
        SecureRandom random = new SecureRandom();

        BigInteger p1 = BigInteger.probablePrime(bitLength, random);
        BigInteger p2 = BigInteger.probablePrime(bitLength, random);

        n = p1.multiply(p2);
        BigInteger phi = p1.subtract(BigInteger.ONE).multiply(p2.subtract(BigInteger.ONE));

        e = new BigInteger("65537");

        // d = e.modInverse(phi);
        d = calculatePrivateKey(e, phi);

        Log.e("RSA", "phi is " + phi);
        Log.e("RSA", "publicKey is " + e);
        Log.e("RSA", "privateKey is " + d);
    }

    public BigInteger encrypt(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        BigInteger m = new BigInteger(bytes);

        return m.modPow(e, this.n);
    }

    public String decrypt(BigInteger encryptedMessage) {
        BigInteger m = encryptedMessage.modPow(d, this.n);

        return new String(m.toByteArray(), StandardCharsets.UTF_8);
    }

    private BigInteger calculatePrivateKey(BigInteger e, BigInteger phi) {
        BigInteger k = new BigInteger("1");
        BigInteger one = new BigInteger("1");

        while (true) {
            BigInteger test = (k.multiply(phi).add(one)).divide(e);

            if (test.multiply(e).subtract(one).divide(phi).equals(k)) {
                return test;
            } else {
                k = k.add(one);
            }
        }
    }
}
