package com.example.demo.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *@ClassName FrameProperties
 *@Author FrameProperties
 *@Date 2019/4/15 10:51
 *@Version 1.0
 * 获取配置文件中信息config
 **/
@ConfigurationProperties(prefix = "frame")
@Component
@Data
@NoArgsConstructor
public class FrameProperties {
    /**
     * 文件
     */
    private File file;

    /**
     * 文件上传、下载相关
     *
     * @author hejiang
     */
    @Data
    @NoArgsConstructor
    public static class File {
        /**
         * 保存路径
         */
        private String saveFilePath;
    }
}
