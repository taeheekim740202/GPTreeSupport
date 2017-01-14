package com.gptree.common.secure.test;

import com.gptree.common.secure.AesUtility;
import org.junit.Test;
import static org.junit.Assert.*;

public class AesUtilTest {

    @Test
    public void randomSaltTest() {
        AesUtility aesUtility = new AesUtility();
        String randomSalt = aesUtility.randomSalt();

        assertNotNull(randomSalt);
    }
}
