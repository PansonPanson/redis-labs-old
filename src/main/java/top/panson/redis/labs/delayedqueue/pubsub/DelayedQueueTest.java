//package top.panson.redis.labs.delayedqueue.pubsub;
//
//import org.redisson.Redisson;
//import org.redisson.api.RBlockingDeque;
//import org.redisson.api.RDelayedQueue;
//import org.redisson.api.RQueue;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @create 2023-08-01 18:15
// * @Author: Panson
// */
//public class DelayedQueueTest {
//    public static void main(String[] args) throws InterruptedException {
//        Logger log = LoggerFactory.getLogger(DelayedQueueTest.class);
//
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://172.31.239.246:6379");
//        RedissonClient redissonClient = Redisson.create(config);
//        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque("one.delayed.queue");
//        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
//        //添加延时消息
//        delayedQueue.offer("测试消息1", 2, TimeUnit.SECONDS);
//        delayedQueue.offerAsync("测试消息offerAsync", 5, TimeUnit.SECONDS);
//        //获取延时消息
//        while (true) {
//            String msg = blockingDeque.take();
//            log.debug("获取延时队列消息:{}",msg);
//        }
//        // 创建 Redisson 客户端
//        RedissonClient redisson = Redisson.create();
//
//        // 获取 Redisson RDelayedQueue 对象
//        RDelayedQueue<String> delayedQueue = redisson.getDelayedQueue(redisson.getQueue("myQueue"));
//
//        // 将消息放入延迟队列中，并设置延迟时间为 10 秒
//        delayedQueue.offer("Hello World!", 10, TimeUnit.SECONDS);
//
//        // 创建 Redisson RQueue 对象
//        RQueue<String> queue = redisson.getQueue("myQueue");
//
//        // 消费队列中的消息
//        while (true) {
//            String message = queue.poll();
//            if (message != null) {
//                // 处理消息
//                System.out.println("Received message: " + message);
//            }
//            // 休眠 1 秒钟，等待新消息到达
//            Thread.sleep(1000);
//        }
//    }
//}