package junit.test;

import com.riter.atcrowdfunding.utils.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class JedisTestDemo {

    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedis();
        Map<String, String> map = jedis.hgetAll("user:1");
        for (Map.Entry<String,String> entry:map.entrySet()){
            System.out.println("key = "+ entry.getKey()+"\tvalue:"+entry.getValue());
        }
    }

}
