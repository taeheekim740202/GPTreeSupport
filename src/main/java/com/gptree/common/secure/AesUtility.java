package com.gptree.common.secure;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecureRandom;

/**
 * AES 암호화 처리
 */
@Controller
@RequestMapping("/security")
public class AesUtility {

    /**
     * Key Size
     */
    private static final int KEY_SIZE = 256;

    /**
     * Hex --> String
     *
     * @param convertBytes
     * @return
     */
    private static String convertHexToString(byte[] convertBytes) {
        return Hex.encodeHexString(convertBytes);
    }

    /**
     * String --> Hex
     *
     * @param convertString
     * @return
     * @throws DecoderException
     */
    private static byte[] convertStringToHex(String convertString) throws DecoderException {
        return Hex.decodeHex(convertString.toCharArray());
    }

    /**
     *
     * @return
     */
    public String randomSalt() {
        return randomSalt(30);
    }

    private String randomSalt(int length) {
        byte[] salt = new byte[length];
        new SecureRandom().nextBytes(salt);

        return convertHexToString(salt);
    }
}
