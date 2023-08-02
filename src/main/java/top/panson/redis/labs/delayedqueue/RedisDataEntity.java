package top.panson.redis.labs.delayedqueue;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZRH
 * @Date: 2022/1/10 11:54
 */
@Data
public class RedisDataEntity<T> implements Serializable {

    /**
     * 数据
     */
    private final T data;
    /**
     * 过期时间（单位：毫秒）
     */
    private final Long expire;
    /**
     * 添加时间
     */
    private final Long time;

    public RedisDataEntity (T data, Long expire, Long time) {
        this.data = data;
        this.expire = expire;
        this.time = time;
    }
}