package top.abosen.toys.arthasdemo.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author qiubaisen
 * @date 2020/7/12
 */

@Component
public class ApplicationContextHelper {
    public static ApplicationContext applicationContext;

    public ApplicationContextHelper(@Autowired ApplicationContext applicationContext) {
        ApplicationContextHelper.applicationContext = applicationContext;
    }
}
