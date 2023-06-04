package br.com.eicon.teste.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private InicialService inicialService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            inicialService.loadInitialData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
