package top.abosen.toys.arthasdemo.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.abosen.toys.arthasdemo.triggers.TriggerComponent;
import top.abosen.toys.arthasdemo.triggers.VoidTrigger;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qiubaisen
 * @date 2020/7/13
 */
@Slf4j
@Component
public class DeadLock {

    // 为了避免杀死进程来释放死锁
    private final List<Thread> deadThreads = new ArrayList<>();

    @SneakyThrows
    private void sleep(long time) {
        Thread.sleep(time);
    }

    @PostConstruct
    public void init() {
        TriggerComponent.register(
                new VoidTrigger("dead-lock", this::doDeadLock),
                new VoidTrigger("release-dead-lock", this::releaseDeadLock));
    }

    public void doDeadLock() {
        ReentrantLock resourceA = new ReentrantLock();
        ReentrantLock resourceB = new ReentrantLock();

        Thread threadA = new Thread(() -> {
            resourceA.lock();
            log.info(Thread.currentThread() + " get ResourceA");
            sleep(1000);
            log.info(Thread.currentThread() + " try to lock ResourceB");
            try {
                resourceB.lockInterruptibly();
            } catch (InterruptedException e) {
                log.info(Thread.currentThread() + " interrupted!");
                resourceA.unlock();
                return;
            }
            log.info(Thread.currentThread() + " get ResourceB");
        });
        deadThreads.add(threadA);
        threadA.start();

        Thread threadB = new Thread(() -> {
            resourceB.lock();
            log.info(Thread.currentThread() + " get ResourceB");
            sleep(1000);
            log.info(Thread.currentThread() + " try to lock ResourceA");
            try {
                resourceA.lockInterruptibly();
            } catch (InterruptedException e) {
                log.info(Thread.currentThread() + " interrupted!");
                resourceB.unlock();
                return;
            }
            log.info(Thread.currentThread() + " get ResourceA");
        });
        deadThreads.add(threadB);
        threadB.start();
    }

    public void releaseDeadLock() {
        deadThreads.forEach(Thread::interrupt);
    }

}
