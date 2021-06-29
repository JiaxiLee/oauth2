package com.li.oauth.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author mall
 * @date 2019/1/4
 */
@Setter
@Getter
@RefreshScope
@Configuration
public class SecurityProperties {

    private PermitProperties ignore = new PermitProperties();

    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

}
