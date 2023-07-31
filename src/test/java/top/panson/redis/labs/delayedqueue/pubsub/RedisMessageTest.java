package top.panson.redis.labs.delayedqueue.pubsub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.panson.redis.labs.util.util.RedisUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @create 2023-07-31 18:02
 * @Author: Panson
 */
@SpringBootTest
public class RedisMessageTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test(){
        final String TOPIC_NAME1 = "TEST_TOPIC1"; // 订阅主题
        final String TOPIC_NAME2 = "TEST_TOPIC2"; // 订阅主题
        // 发布消息
        MessageDto dto = new MessageDto();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        dto.setData(timeFormatter.format(now));
        dto.setTitle("日常信息");
        dto.setContent("hello world!");
 redisUtil.set("key1", "v1");
        redisUtil.convertAndSendNew(TOPIC_NAME1, dto);

    }
}
