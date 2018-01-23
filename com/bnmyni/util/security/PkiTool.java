package com.aspire.hdc.common.security.util;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import org.apache.commons.codec.binary.Base64;

/**
* hwx 加密工具类
**/
public class PkiTool {

	public static byte[] cryptMD5(byte[] input) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(input);
		return md.digest();
	}

	public static String encodeBase64(byte[] src) throws Exception {
		Base64 base64 = new Base64();
		return new String(base64.encode(src));
	}

	public static byte[] decodeBase64(byte[] src) {
		Base64 base64 = new Base64();
		return base64.decode(src);
	}

	public static byte[] dsaSigned(byte[] message, PrivateKey privkey)
			throws Exception {
		Signature signalg = Signature.getInstance("DSA");
		signalg.initSign(privkey);
		signalg.update(message);
		return signalg.sign();
	}

	public static boolean verify(byte[] message, byte[] signature,
			PublicKey pubkey) throws Exception {
		Signature verifyalg = Signature.getInstance("DSA");
		verifyalg.initVerify(pubkey);
		verifyalg.update(message);
		return verifyalg.verify(signature);
	}
	public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;

        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}