package top.abosen.toys.arthasdemo.ognl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.abosen.toys.arthasdemo.triggers.Trigger;
import top.abosen.toys.arthasdemo.triggers.TriggerComponent;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author qiubaisen
 * @date 2020/7/15
 */
@Component
public class TraceDemo {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        TriggerComponent.register(new Trigger<>("analyze-user", () -> this.analyze(100)));
    }

    public Statistics analyze(int n) {
        List<User> collect = IntStream.range(0, n).mapToObj(x -> userRepository.getOne()).collect(Collectors.toList());
//         List<User> collect = userRepository.getMany(n); //使用redefine优化

        Statistics.StatisticsHolder statisticsHolder = Statistics.start();
        collect.forEach(statisticsHolder::add);
        return statisticsHolder.stop();
    }

}
