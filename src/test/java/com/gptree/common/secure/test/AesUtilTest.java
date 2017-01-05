package com.gptree.common.secure.test;

import com.gptree.common.secure.AesUtility;
import junit.framework.TestCase;
import org.junit.Test;

public class AesUtilTest extends TestCase {

    @Test
    public void randomSaltTest() {
        AesUtility aesUtility = new AesUtility();
        String randomSalt = aesUtility.randomSalt();

        System.out.println(randomSalt);
        assertNotNull(randomSalt);
    }
}
