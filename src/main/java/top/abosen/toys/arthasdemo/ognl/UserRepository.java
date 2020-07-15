package top.abosen.toys.arthasdemo.ognl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 模拟一些耗时操作
 *
 * @author qiubaisen
 * @date 2020/7/15
 */

@Repository
public class UserRepository {


    public User getOne() {
        slowWork(10);
        return User.random();
    }

    public List<User> getMany(int n) {
        slowWork(10, 50);
        return IntStream.range(0, n).mapToObj(x -> User.random()).collect(Collectors.toList());
    }


    private void slowWork(long time) {
        slowWork(0, time);
    }

    @SneakyThrows
    private void slowWork(long min, long max) {
        Thread.sleep(ThreadLocalRandom.current().nextLong(min, max));
    }

}
