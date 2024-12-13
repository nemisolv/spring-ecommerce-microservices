package net.nemisolv.lib.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    void set(String key, Object value, long time);

    void set(String key, Object value);

    Object get(String key);

    Boolean del(String key);

    Long del(List<String> keys);

    Boolean expire(String key, long time);

    Long getExpire(String key);

    Boolean hasKey(String key);

    Long incr(String key, long delta);

    Long decr(String key, long delta);
    Object hGet(String key, String hashKey);

    Boolean hSet(String key, String hashKey, Object value, long time);

    void hSet(String key, String hashKey, Object value);

    Map<Object, Object> hGetAll(String key);

    Boolean hSetAll(String key, Map<String, Object> map, long time);

    void hSetAll(String key, Map<String, ?> map);

    void hDel(String key, Object... hashKey);

    Boolean hHasKey(String key, String hashKey);

    Long hIncr(String key, String hashKey, Long delta);

    Long hDecr(String key, String hashKey, Long delta);

    Set<Object> sMembers(String key);

    Long sAdd(String key, Object... values);

    Long sAdd(String key, long time, Object... values);

    Boolean sIsMember(String key, Object value);

    Long sSize(String key);

    Long sRemove(String key, Object... values);

    List<Object> lRange(String key, long start, long end);

    Long lSize(String key);

    Object lIndex(String key, long index);

    Long lPush(String key, Object value);

    Long lPush(String key, Object value, long time);

    Long lPushAll(String key, Object... values);

    Long lPushAll(String key, Long time, Object... values);

    Long lRemove(String key, long count, Object value);
}
