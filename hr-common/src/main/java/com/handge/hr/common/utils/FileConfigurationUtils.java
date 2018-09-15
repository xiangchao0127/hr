package com.handge.hr.common.utils;

import com.handge.hr.exception.custom.UnifiedException;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Created by MaJianfu on 2018/8/24.
 */
public class FileConfigurationUtils {

    public static final String FILE_PATH="com/handge/hr/manage/template/file.properties";

    public static  String getConfig(String keyName){
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setEncoding("UTF-8")
                                .setFileName(FILE_PATH));
        try {
            Configuration config = builder.getConfiguration();
            String valueName = config.getString(keyName);
            return valueName;
        } catch (ConfigurationException e) {
            throw new UnifiedException(e);
        }
    }
}
