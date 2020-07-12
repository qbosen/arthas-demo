package top.abosen.toys.arthasdemo.profiler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * profiler -d 10 -e alloc -f output-alloc.svg start
 * profiler -d 10 -e cpu -f output-cpu.svg  start
 * profiler -d 10 -f output-cpu.svg  start
 * profiler -d 10 -e itimer -f output-itimer.svg  start
 * profiler -d 10 -e wall -f output-wall.svg  start
 * profiler list
 * 模拟热点代码
 * 参考文章 : https://www.jianshu.com/p/918e1dce61cd
 *
 * @author qiubaisen
 * @date 2020/7/12
 */
@Component
@Slf4j
public class HotCode {
    private static volatile int value;
    private static Object array;
    private static volatile boolean condition = true;

    public void hotMethodRun() {
        condition = true;
        while (condition) {
            HotCode.random();
            HotCode.add();
            HotCode.uuid();
            HotCode.allocate();
        }
    }
    public void stopHotMethod(){
        condition = false;
    }


    /**
     * 生成 大长度的数组
     */
    private static void allocate() {
        array = new int[6 * 100];
        array = new Integer[6 * 100];
    }


    /**
     * 生成一个UUID
     */
    private static void uuid() {
        ArrayList<String> list = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replace("-", "");
        list.add(str);
    }

    /**
     * 数字累加
     */
    private static void add() {
        value++;
    }

    /**
     * 生成一个随机数
     */
    private static void random() {
        Random random = new Random();
        int anInt = random.nextInt();
    }


}
