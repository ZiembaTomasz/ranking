package com.ziembatomasz.ranking.service;

import com.ziembatomasz.ranking.dto.Player;
import com.ziembatomasz.ranking.mapper.PlayerMapper;
import com.ziembatomasz.ranking.repository.PlayerRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RankingServiceTest {
    @InjectMocks
    private RankingService rankingService;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ZSetOperations<String, String> zSetOperations;
    @Mock
    private PlayerMapper playerMapper;
    @Mock
    private PlayerRepository playerRepository;

    @Test
    public void shouldUpdateRankTest() {
        //Given
        long userId = 347L;
        Double score = 10.5;
        String key = "Long Jump";
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(zSetOperations.rank(key, String.valueOf(userId))).thenReturn(1L);
        //When
        long myId = rankingService.updateRank(userId, score);
        //Then
        assertEquals(1L, myId);
    }

    @Test
    public void shouldGetRanking() {
        //Given
        Long startPosition = 1L;
        Long endPosition = 999L;
        Player player = new Player("George Ptac", "347", 11.02);
        TypedTuple typedTuple = mock(TypedTuple.class);
        String key = "Long Jump";
        Set<TypedTuple<String>> tulpedSet = new HashSet<>();
        tulpedSet.add(typedTuple);
        Set<Player> myPlayers = new HashSet<>();
        myPlayers.add(player);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(zSetOperations.rangeWithScores(key, startPosition, endPosition)).thenReturn(tulpedSet);
        when(playerMapper.mapToPlayer(tulpedSet)).thenReturn(myPlayers);
        //When
        Set<Player> newSet = rankingService.ranking();
        //Then
        assertEquals(1, newSet.size());
    }

    @Test
    public void shouldAddPlayerScoreToRanking() {
        //Given
        Player player = new Player("George Ptac", "347", 11.02);
        long userId = 347L;
        double score = 10.5;
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(playerRepository.getOne(userId)).thenReturn(player);
        //When
        rankingService.addPlayerScoreToJump(userId, score);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    public void shouldGetRangeByScores() {
        //Given
        Player player = new Player("George Ptac", "347", 11.02);
        Long startPosition = 1L;
        Long endPosition = 999L;
        String key = "Long Jump";
        TypedTuple typedTuple = mock(TypedTuple.class);
        Set<TypedTuple<String>> tulpedSet = new HashSet<>();
        tulpedSet.add(typedTuple);
        Set<Player> myPlayers = new HashSet<>();
        myPlayers.add(player);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(zSetOperations.rangeByScoreWithScores(key, startPosition, endPosition)).thenReturn(tulpedSet);
        when(playerMapper.mapToPlayer(tulpedSet)).thenReturn(myPlayers);
        //When
        Set<Player> newSet = rankingService.getRangeByScores(startPosition, endPosition);
        //Then
        assertEquals(1, newSet.size());
    }
}