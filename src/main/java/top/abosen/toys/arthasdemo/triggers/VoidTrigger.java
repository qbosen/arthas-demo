package top.abosen.toys.arthasdemo.triggers;

/**
 * @author qiubaisen
 * @date 2020/7/12
 */
public class VoidTrigger extends Trigger<Void> {
    public VoidTrigger(String key, Runnable runnable) {
        super(key, () -> {
            runnable.run();
            return null;
        });
    }
}
