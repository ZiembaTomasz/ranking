package com.ziembatomasz.ranking.controller;

import com.ziembatomasz.ranking.dto.Player;
import com.ziembatomasz.ranking.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/LongJump")
public class LongJumpRankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping(value = "getRanking")
    public Set<Player> getRanking(@RequestParam String ranking) {
        return rankingService.ranking();
    }

    @PutMapping(value = "updateRanking")
    public Long updateRanking(@RequestParam Long userId, @RequestParam Double score) {
        return rankingService.updateRank(userId, score);
    }

    @DeleteMapping(value = "deleteUser")
    public void deleteUser(@RequestParam Long userId) {
        rankingService.removePlayer(userId);
    }

    @PostMapping(value = "addPlayer")
    public void addPlayerToRanking(String ranking, Long userId, double score) {
        rankingService.addPlayerScoreToJump(userId, score);
    }

    @GetMapping(value = "/ranking/{ranking}/user/{userId}/score{score}")
    public Set<Player> getPlayersByScoresRange(@PathVariable String ranking, @PathVariable Long startPosition, @PathVariable Long endPosition) {
        return rankingService.getRangeByScores(startPosition, endPosition);
    }

    @GetMapping(value = "ranking/longJump")
    public Set<String> getPlayersIds(@RequestParam Long startPosition, @RequestParam Long endPosition) {
        return rankingService.getUsersId(startPosition, endPosition);
    }
}
