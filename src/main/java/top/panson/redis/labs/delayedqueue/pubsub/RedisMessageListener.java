package top.panson.redis.labs.delayedqueue.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @create 2023-07-31 17:47
 * @Author: Panson
 */
@Component
public class RedisMessageListener implements MessageListener {

    private Logger log = LoggerFactory.getLogger(RedisMessageListener.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        // 接收的topic
        log.info("channel:" + new String(pattern));

        //序列化对象（特别注意：发布的时候需要设置序列化；订阅方也需要设置序列化）
        MessageDto messageDto = (MessageDto) redisTemplate.getValueSerializer().deserialize(message.getBody());
        log.info(messageDto.getData()+","+messageDto.getContent());
    }
}
