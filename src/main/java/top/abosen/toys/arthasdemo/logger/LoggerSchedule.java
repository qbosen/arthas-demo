package top.abosen.toys.arthasdemo.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.abosen.toys.arthasdemo.triggers.TriggerComponent;
import top.abosen.toys.arthasdemo.triggers.VoidTrigger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author qiubaisen
 * @date 2020/7/13
 */
@Component
@Slf4j
public class LoggerSchedule {

    private static final ScheduledExecutorService loggerSchedule = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> future = null;

    @PostConstruct
    public void init() {
        TriggerComponent.register(
                new VoidTrigger("start-logger", this::startLog),
                new VoidTrigger("stop-logger", this::stopLog));
    }

    public void startLog() {
        if (future != null) return;

        future = loggerSchedule.scheduleAtFixedRate(() -> {
            log.trace(">>trace");
            log.debug(">>debug");
            log.warn(">>info");
            log.info(">>warn");
            log.error(">>error");
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stopLog() {
        if (future == null) return;
        future.cancel(false);
        future = null;
    }

    @PreDestroy
    public void close() {
        loggerSchedule.shutdown();
    }
}
