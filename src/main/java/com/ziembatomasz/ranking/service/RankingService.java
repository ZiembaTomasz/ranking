package com.ziembatomasz.ranking.service;

import lombok.AllArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RankingService {

    RedisTemplate<String, String> redisTemplate;



    public Long updateRank(Long userId, double score) {
        // Because the default score of Zset is small in front, we take the score back, so the larger the user's score is, the smaller the corresponding score is, and the higher the ranking is.
        redisTemplate.opsForZSet().add("test", String.valueOf(userId), -score);
        Long rank = redisTemplate.opsForZSet().rank("test", String.valueOf(userId));
        System.out.println(rank);
        return rank;
    }

}
