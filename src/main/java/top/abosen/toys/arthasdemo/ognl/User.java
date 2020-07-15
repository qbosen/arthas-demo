package top.abosen.toys.arthasdemo.ognl;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

/**
 * @author qiubaisen
 * @date 2020/7/14
 */

@Data
@Builder
public class User {
    private String name;
    private LocalDate birthday;
    private boolean male;
    private int age;



    private static final Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);

    public static User random(){
        LocalDate birthday = faker.date().birthday(10, 25).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return User.builder()
                .name(faker.name().name())
                .birthday(birthday)
                .male(faker.bool().bool())
                .age(LocalDate.now().getYear() - birthday.getYear())
                .build();
    }
}
