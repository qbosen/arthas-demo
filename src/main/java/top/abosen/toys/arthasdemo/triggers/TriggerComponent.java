package top.abosen.toys.arthasdemo.triggers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.abosen.toys.arthasdemo.context.ApplicationContextHelper;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author qiubaisen
 * @date 2020/7/12
 */

@RestController
@Slf4j
public class TriggerComponent {
    @GetMapping("/{trigger}")
    public Object router(@PathVariable String trigger) throws Exception {
        Trigger<?> call = triggerMap.get(trigger);
        if (call == null) {
            log.warn("没有找到对应的Trigger: {}", trigger);
            return "Trigger Not Found";
        }
        log.info("调用Trigger: {}", trigger);
        Object result = call.getCallable().call();
        return Optional.ofNullable(result).orElse("OK");
    }

    private final Map<String, Trigger<?>> triggerMap = new HashMap<>();

    @PostConstruct
    void initTriggers() {
        ApplicationContextHelper.applicationContext.getBeansOfType(Trigger.class).values()
                .forEach(trigger -> triggerMap.put(trigger.getKey(), trigger));

        log.info("Triggers: {}", triggerMap.keySet());
    }
}
