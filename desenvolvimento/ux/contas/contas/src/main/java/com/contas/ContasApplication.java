package com.contas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.time.ZoneId;

@SpringBootApplication
public class ContasApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ContasApplication.class);
    }
    public static void main(String[] args) {
//        SpringApplication application = new SpringApplication(ContasApplication.class);
//        application.setWebApplicationType(WebApplicationType.SERVLET);
        SpringApplication.run(ContasApplication.class, args);
    }

    /*-------------------------------------------------------------------
     * 		 					ATTRIBUTES
     *-------------------------------------------------------------------*/
    /**
     *
     */
    public static final String AUDIT_SCHEMA = "auditing";

    /**
     *
     */
    public static ZoneId fusoHorarioDeSaoPaulo = ZoneId.of("America/Sao_Paulo");

    /*-------------------------------------------------------------------
     * 		 					OVERRIDES
     *-------------------------------------------------------------------*/

    /*-------------------------------------------------------------------
     * 		 						BEANS
     *-------------------------------------------------------------------*/

    /**
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setAlwaysUseMessageFormat(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("classpath:i18n/exceptions", "classpath:i18n/labels", "classpath:i18n/messages");
        return messageSource;
    }


    /**
     * @return
     */
    @Bean
    public ConcurrentTaskScheduler concurrentTaskScheduler() {
        return new ConcurrentTaskScheduler();
    }

}
