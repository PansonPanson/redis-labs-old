package top.panson.redis.labs.delayedqueue;

import io.netty.util.HashedWheelTimer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * redisson延迟队列处理器
 *
 * @author zrh
 */
@Slf4j
@Component
public class RedissonQueueHandle implements InitializingBean {

    private final RBlockingQueue<RedisDataEntity<?>> queue;
    private final RDelayedQueue<RedisDataEntity<?>> delayedQueue;
    public RedissonQueueHandle (RedissonClient client) {
        this.queue = client.getBlockingQueue("redisson:queue");
        this.delayedQueue = client.getDelayedQueue(queue);
    }

    @Override
    public void afterPropertiesSet () {

        // 开一个线程阻塞式获取任务
        thread();

        // 使用netty时间轮循环获取任务
//        watchDog(new HashedWheelTimer());

        // 使用线程池定时获取任务
//        schedule();
    }

    private void thread () {
        new Thread(() -> {
            while (true) {
                try {
                    RedisDataEntity entity = queue.take();
                    log.info("本次获取数据：{}，耗时：{}", entity, System.currentTimeMillis() - entity.getTime());
                } catch (Exception e) {
                }
            }
        }, "zrh").start();
    }

    private void watchDog (final HashedWheelTimer timer) {
        timer.newTimeout(timeout -> {
            RedisDataEntity entity = queue.poll();
            if (null != entity) {
                log.info("本次获取数据：{}，耗时：{}", entity, System.currentTimeMillis() - entity.getTime());
            }
            watchDog(timer);
        }, 3, TimeUnit.SECONDS);
    }

    private void schedule () {
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {
            RedisDataEntity entity = queue.poll();
            if (null != entity) {
                log.info("本次获取数据：{}，耗时：{}", entity, System.currentTimeMillis() - entity.getTime());
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    /**
     * 放入redis，定时过期
     *
     * @param entity
     */
    public void offer (RedisDataEntity entity) {
        try {
            delayedQueue.offer(entity, entity.getExpire(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("放入redis延迟队列异常", e);
        }
    }
}