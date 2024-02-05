package top.abosen.toys.arthasdemo.ognl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiubaisen
 * @since 2024/2/4
 */

@RestController
@RequiredArgsConstructor
public class WatchSqlDemo {
    @Mapper
    public interface UserMapper {
        @Select("select id, name  from users where id = #{id}")
        User findById(@Param("id") long state);

    }

    final UserMapper userMapper;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") long id) {
        return userMapper.findById(id);
    }


}
