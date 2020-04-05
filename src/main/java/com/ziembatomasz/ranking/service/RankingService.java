package com.ziembatomasz.ranking.service;

import com.ziembatomasz.ranking.dto.Player;
import com.ziembatomasz.ranking.mapper.PlayerMapper;
import com.ziembatomasz.ranking.repository.PlayerRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class RankingService {
    private final static String RANKING = "Long Jump";
    private PlayerMapper playerMapper;
    private PlayerRepository playerRepository;

    RedisTemplate<String, String> redisTemplate;

    public Long updateRank(Long userId, double score) {
        // Because the default score of Zset is small in front, we take the score back, so the larger the user's score is, the smaller the corresponding score is, and the higher the ranking is.
        redisTemplate.opsForZSet().add(RANKING, String.valueOf(userId), -score);
        Long rank = redisTemplate.opsForZSet().rank("Long Jump", String.valueOf(userId));
        System.out.println(rank);
        return rank;
    }

    public void removePlayer(Long playerId) {
        redisTemplate.opsForZSet().remove("Long Jump", playerId);
    }

    public void addPlayerScoreToJump(Long userId, double score) {
        redisTemplate.opsForZSet().add(RANKING, String.valueOf(userId), score);
        Player player = playerRepository.getOne(userId);
        player.setScore(score);
        playerRepository.save(player);
    }

    public Set<Player> ranking() {
        Long startPosition = 1L;
        Long endPosition = 999L;
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().rangeWithScores(RANKING, startPosition, endPosition);
        return playerMapper.mapToPlayer(set);
    }

    public Set<Player> getRangeByScores(Long startPosition, Long endPosition) {

        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().rangeByScoreWithScores(RANKING, startPosition, endPosition);
        return playerMapper.mapToPlayer(set);
    }

    public Set<String> getUsersId(Long startPosition, Long endPosition) {
        return redisTemplate.opsForZSet().rangeByScore("Long Jump", startPosition, endPosition);
    }
}
