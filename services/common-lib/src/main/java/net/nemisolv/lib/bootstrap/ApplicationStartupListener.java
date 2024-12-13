package net.nemisolv.lib.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Application started successfully!");

        // log the application version
        String version = event.getSpringApplication().getMainApplicationClass().getPackage().getImplementationVersion();
        log.info("App Version: {}", version != null ? version : "Unknown");

    }
}