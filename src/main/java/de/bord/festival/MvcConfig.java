package de.bord.festival;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/buy_ticket").setViewName("buy_ticket");
        registry.addViewController("/contact_details").setViewName("contact_details");
        registry.addViewController("/events").setViewName("events");
        registry.addViewController("/event_create").setViewName("event_create");
        registry.addViewController("/event_update").setViewName("event_update");
        registry.addViewController("/information_user").setViewName("information_user");
        registry.addViewController("/login_user").setViewName("login_user");
    }

}