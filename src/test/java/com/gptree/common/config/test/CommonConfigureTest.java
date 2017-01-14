package com.gptree.common.config.test;

import com.gptree.common.config.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CommonConfigureTest {

    @Test
    public void getValueTest() {
        System.setProperty("config.file", "D://Work/workspace/intellij/GPTreeSupport/config/gptree_config.xml");
        CommonConfigure config = CommonConfigure.getInstance();

        String testValue = config.getValue("pages/page[@id='PAGE_LOGIN_REQUEST']");
        String testPageValue = config.getPageValue("PAGE_LOGIN_REQUEST");

        assertEquals(testValue, "/login/login.html");
        assertEquals(testValue, testPageValue);


        List<Object> testListValue = config.getList("sessionCheckExtensions/sessionCheckExtension");

        assertTrue((testListValue != null && testListValue.contains("html")));
    }
}
