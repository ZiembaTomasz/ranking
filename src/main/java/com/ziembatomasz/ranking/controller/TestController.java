package com.ziembatomasz.ranking.controller;

import com.ziembatomasz.ranking.service.RankingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RankingService rankingService;

    @GetMapping
    public Long rank() {
        return rankingService.updateRank(1L, 500.0);
    }
}
