package top.abosen.toys.arthasdemo.ognl;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import top.abosen.toys.arthasdemo.triggers.Trigger;
import top.abosen.toys.arthasdemo.triggers.TriggerComponent;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

/**
 * @author qiubaisen
 * @date 2020/7/14
 */
@Component
public class WatchDemo {
    private static final Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);

    @PostConstruct
    public void init() {
        TriggerComponent.register(
                new Trigger<>("create-user", this::supplyUser),
                new Trigger<>("consume-adult", () -> consumeUser(supplyUser())));
    }

    private int userCount = 0;

    public User supplyUser() {
        userCount++;
        LocalDate birthday = faker.date().birthday(10, 25).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return User.builder()
                .name(faker.name().name())
                .birthday(birthday)
                .male(faker.bool().bool())
                .age(LocalDate.now().getYear() - birthday.getYear())
                .build();
    }

    public String consumeUser(User user) {
        if (user.getAge() < 18) throw new UnsupportedOperationException("禁止消费未成年人");
        return user.getName();
    }

}
