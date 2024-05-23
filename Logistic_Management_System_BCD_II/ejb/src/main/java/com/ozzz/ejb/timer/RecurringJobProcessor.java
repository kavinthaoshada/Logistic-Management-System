package com.ozzz.ejb.timer;

import com.ozzz.ejb.remote.OrderManageService;
import jakarta.annotation.Resource;
import jakarta.ejb.*;

@Singleton
@Startup
public class RecurringJobProcessor {
    @Resource
    SessionContext context;

    @EJB
    private OrderManageService orderManageService;

    public void startTimer() {
        context.getTimerService().createTimer(0, 86400000, "RecurringJobTimer");
    }

    public void stopTimer() {
        for (Timer timer : context.getTimerService().getTimers()) {
            if ("RecurringJobTimer".equals(timer.getInfo())) {
                timer.cancel();
            }
        }
    }

    @Timeout
    public void processRecurringJob(Timer timer) {
        orderManageService.processRecurringJob();
    }
}
