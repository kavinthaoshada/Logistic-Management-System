package com.ozzz.ejb.timer;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class ApplicationStartup {
    @EJB
    private RecurringJobProcessor recurringJobProcessor;

    @PostConstruct
    public void initialize() {
        recurringJobProcessor.startTimer();
    }
}
