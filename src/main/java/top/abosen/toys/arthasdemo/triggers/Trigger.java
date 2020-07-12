package top.abosen.toys.arthasdemo.triggers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.Callable;

/**
 * @author qiubaisen
 * @date 2020/7/12
 */

@Getter
@AllArgsConstructor
public class Trigger<V> {
    private final String key;
    private final Callable<V> callable;
}
