package top.abosen.toys.arthasdemo.ognl;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

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
}
