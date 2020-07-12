package top.abosen.toys.arthasdemo.profiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.abosen.toys.arthasdemo.triggers.Trigger;
import top.abosen.toys.arthasdemo.triggers.VoidTrigger;

/**
 * @author qiubaisen
 * @date 2020/7/12
 */

@Component
public class ProfilerTrigger {
    @Autowired
    private HotCode hotCode;

    @Bean
    public Trigger<Void> startProfiler() {
        return new VoidTrigger("start-profiler", () -> new Thread(() -> hotCode.hotMethodRun()).start());
    }

    @Bean
    public Trigger<Void> stopProfiler() {
        return new VoidTrigger("stop-profiler", () -> hotCode.stopHotMethod());
    }
}
