//package org.example.webtoonepics.main.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//
//@Service
//public class RedisService {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    public void printAllKeysAndValues() {
//        // Redis에 저장된 모든 key 가져오기
//        Set<String> keys = redisTemplate.keys("*");
//
//        if (keys != null && !keys.isEmpty()) {
//            for (String key : keys) {
//                // 해당 key에 대한 value 가져오기
//                Object value = redisTemplate.opsForValue().get(key);
//                System.out.println("Key: " + key + ", Value: " + value);
//            }
//        } else {
//            System.out.println("No keys found in Redis.");
//        }
//    }
//}
