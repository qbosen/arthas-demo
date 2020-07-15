package top.abosen.toys.arthasdemo.ognl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.abosen.toys.arthasdemo.triggers.Trigger;
import top.abosen.toys.arthasdemo.triggers.TriggerComponent;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author qiubaisen
 * @date 2020/7/14
 */
@Component
public class WatchDemo {

    @PostConstruct
    public void init() {
        TriggerComponent.register(
                new Trigger<>("create-user", this::supplyUser),
                new Trigger<>("consume-adult", () -> consumeUser(supplyUser())),
                new Trigger<>("complex-user", () -> adultUserGroup(IntStream.range(0, 20).mapToObj(x -> User.random()).collect(Collectors.toList())))
        );
    }


    private int userCount = 0;

    public User supplyUser() {
        userCount++;
        return User.random();
    }

    public String consumeUser(User user) {
        if (user.getAge() < 18) throw new UnsupportedOperationException("禁止消费未成年人");
        return user.getName();
    }

    public Map<Integer, List<User>> adultUserGroup(List<User> users) {
        return users.stream().filter(u -> u.getAge() > 18).collect(Collectors.groupingBy(User::getAge));
    }
}
