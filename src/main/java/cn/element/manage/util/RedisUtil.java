package cn.element.manage.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  key 键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 键  不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true  存在  false  不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值或者多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 普通缓存放入并设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间(秒) time要大于0 如果小于0 那么将会设置无限期
     * @return true成功 false失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 变化量(>0)
     */
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("delta must be greater than 0!");
        }

        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 变化量(>0)
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("delta must be greater than 0!");
        }

        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * hash数据类型的获取
     *
     * @param key  键  NotNull
     * @param item 值  NotNull
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmGet(String key) {

        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hash数据类型的设置
     *
     * @param key 键
     * @param map 对应的多个键值
     */
    public boolean hmSet(String key, Map<String, Object> map) {

        try {
            redisTemplate.opsForHash().putAll(key, map);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * hash数据类型 set并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmSet(String key, Map<String, Object> map, long time) {

        try {
            redisTemplate.opsForHash().putAll(key, map);

            if (time > 0) {
                expire(key, time);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在则创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true成功 false失败
     */
    public boolean hSet(String key, String item, Object value) {

        try {
            redisTemplate.opsForHash().put(key, item, value);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 向一张hash表中放入数据并设置时间,如果不存在则创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)
     * @return true成功 false失败
     */
    public boolean hSet(String key, String item, Object value, long time) {

        try {
            redisTemplate.opsForHash().put(key, item, value);

            if (time > 0) {
                expire(key, time);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键  NotNull
     * @param item 项  可以是多个  NotNull
     */
    public void hDel(String key, Object... item) {

        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键   NotNull
     * @param item 项   NotNull
     * @return true存在  false不存在
     */
    public boolean hHashKey(String key, String item) {

        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,则会创建一个,并把新增的值返回
     *
     * @param key   键
     * @param item  项
     * @param delta 增量(>0)
     */
    public double hIncrement(String key, String item, double delta) {

        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * hash递减 如果不存在,则会创建一个,并把新增的值返回
     *
     * @param key   键
     * @param item  项
     * @param delta 增量(>0)
     */
    public double hDecrement(String key, String item, double delta) {

        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * 根据key获取set中的所有值
     *
     * @param key 键
     */
    public Set<Object> sGet(String key) {

        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {

        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值  can be multiple
     * @return 成功的个数
     */
    public long sSet(String key, Object... values) {

        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }

    /**
     * 将数据放入set缓存并设置过期时间
     *
     * @param key    键
     * @param time   过期时间(s)
     * @param values 值  can be multiple
     * @return 成功的个数
     */
    public long sSet(String key, long time, Object... values) {

        try {
            Long count = redisTemplate.opsForSet().add(key, values);

            if (time > 0) {
                expire(key, time);
            }

            return count;
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }

    /**
     * 获取set的元素个数
     *
     * @param key 键
     * @return 元素个数
     */
    public long sGetSize(String key) {

        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 (multiple)
     * @return 移除的个数
     */
    public long sRemove(String key, Object... values) {

        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束0到-1表示所有的值
     */
    public List<Object> lGet(String key, long start, long end) {

        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 获取list的元素个数
     *
     * @param key 键
     * @return 元素个数
     */
    public long lGetSize(String key) {

        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }

    /**
     * 通过索引获取list中的值
     *
     * @param key   键
     * @param index 索引 index >= 0 时,0 表示表头.index < 0时 -1表示表尾
     */
    public Object lGetIndex(String key, long index) {

        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 放入list缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, Object value) {

        try {
            redisTemplate.opsForList().rightPush(key, value);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 放入list缓存并设置过期时间
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, Object value, long time) {

        try {
            redisTemplate.opsForList().rightPush(key, value);

            if (time > 0) {
                expire(key, time);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 放入list缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, List<Object> value) {

        try {
            redisTemplate.opsForList().rightPushAll(key, value);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 放入list缓存并设置过期时间
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, List<Object> value, long time) {

        try {
            redisTemplate.opsForList().rightPushAll(key, value);

            if (time > 0) {
                expire(key, time);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public boolean lUpdateIndex(String key, long index, Object value) {

        try {
            redisTemplate.opsForList().set(key, index, value);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 移除N个值为value的元素
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {

        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }


}
