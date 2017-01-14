package com.gptree.common.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.lang.SystemUtils;

import java.io.File;
import java.util.List;

import static com.gptree.common.config.ConfigFileType.*;

/**
 * 공통 환경 파일을 로드한다.
 */
public class CommonConfigure {

    /**
     * 환경파일 Instance
     */
    private static CommonConfigure instance;

    /**
     * 환경파일 객체
     */
    private Configuration configuration;

    /**
     * 환경파일 유형
     */
    private ConfigFileType type;

    /**
     * 생성자
     *
     * @param fileName 파일명
     * @throws ConfigurationException
     */
    private CommonConfigure(String fileName) throws ConfigurationException {
        this.type = getType(fileName);

        FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
        strategy.setRefreshDelay(1 * 5000);

        if (type == null)
            throw new IllegalArgumentException("Config file is null");
        else switch (type) {
            case PROPERTY:
                configuration = new PropertiesConfiguration(fileName);
                ((PropertiesConfiguration) configuration).setReloadingStrategy(strategy);
                break;
            case XML:
                configuration = new XMLConfiguration(fileName);
                ((XMLConfiguration) configuration).setReloadingStrategy(strategy);
                ((XMLConfiguration) configuration).setExpressionEngine(new XPathExpressionEngine());
                break;
        }
    }

    /**
     * 싱글턴으로 환경파일 객체를 생성한다.
     *
     * @return
     */
    public static synchronized CommonConfigure getInstance() {
        if (instance == null) {
            String configFileName = System.getProperty("config.file");

            if (configFileName == null)
                configFileName = String.format("%s%s%s", SystemUtils.getUserDir().getAbsolutePath(), File.pathSeparator, "gptree_config.xml");
            try {
                instance = new CommonConfigure(configFileName);
            } catch (ConfigurationException e) {}
        }

        return instance;
    }

    /**
     * 환경 파일에 설정된 값을 가져온다.
     *
     * @param key 키명
     * @return 설정된 값
     */
    public String getValue(String key) {
        return (configuration != null) ? configuration.getString(key) : "";
    }

    /**
     * 설정된 값의 리스트를 가져온다.
     *
     * @param key 키명
     * @return 설정된 값
     */
    public List<Object> getList(String key) { return (configuration != null) ? configuration.getList(key) : null; }

    /**
     * 페이지 변수를 가져온다.
     *
     * @param key 키명
     * @return 설정된 값
     */
    public String getPageValue(String key) {
        return getValue(String.format("pages/page[@id='%s']", key));
    }

    /**
     * Session 변수명을 가져온다.
     *
     * @param key 키명
     * @return 설정된 값
     */
    public String getSessionName(String key) { return getValue(String.format("sessions/session[@id='%s']", key)); }

    /**
     * 환경파일의 형식을 이용하여 환경설정 객체를 반환한다.
     *
     * @param fileName 파일명
     * @return 파일타입
     */
    static ConfigFileType getType(String fileName) {
        if (fileName != null && fileName.toLowerCase().endsWith("xml"))
            return XML;
        else if (fileName != null)
            return PROPERTY;

        return null;
    }
}
