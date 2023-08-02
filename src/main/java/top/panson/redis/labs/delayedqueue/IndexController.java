package top.panson.redis.labs.delayedqueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @create 2023-08-02 14:16
 * @Author: Panson
 */
@Slf4j
@RestController
public class IndexController {

    private final RedissonQueueHandle redisHandle;

    public IndexController (RedissonQueueHandle redisHandle) {
        this.redisHandle = redisHandle;
    }

    @PostMapping("redissonQueue")
    public String redissonQueue (@RequestParam String data, @RequestParam Long expire) {
        RedisDataEntity entity = new RedisDataEntity(data, expire, System.currentTimeMillis());
        log.info("本次添加数据：{}", entity);
        redisHandle.offer(entity);
        return "ok";
    }
}
