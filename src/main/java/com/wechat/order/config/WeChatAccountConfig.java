package com.wechat.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zzy on 2017/12/17.
 */
@Data
@Component
@ConfigurationProperties(prefix = "weChat")
public class WeChatAccountConfig {

    private String mpAppId;

    private String mpAppSecret;
}
