package de.bord.festival;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/admin_menu").setViewName("admin_menu");
        registry.addViewController("/buy_ticket_user").setViewName("buy_ticket_user");
        registry.addViewController("/ticket_buy_ok").setViewName("ticket_buy_ok");
        registry.addViewController("/ticket_buy_error").setViewName("ticket_buy_error");
        registry.addViewController("/contact_details").setViewName("contact_details");
        registry.addViewController("/event_update").setViewName("event_update");
        registry.addViewController("/information_user").setViewName("information_user");
        registry.addViewController("/user_menu").setViewName("user_menu");
    }

}