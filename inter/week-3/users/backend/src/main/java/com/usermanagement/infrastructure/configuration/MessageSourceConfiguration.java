package com.usermanagement.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author Timur Berezhnoi
 */
@Configuration
public class MessageSourceConfiguration {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        String messageSource = "classpath:i18n/messages";

        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename(messageSource);
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }
}
