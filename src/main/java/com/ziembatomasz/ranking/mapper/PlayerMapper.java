package com.ziembatomasz.ranking.mapper;

import com.ziembatomasz.ranking.dto.Player;
import com.ziembatomasz.ranking.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Component
public class PlayerMapper {
    private PlayerRepository playerRepository;

    public Set<Player> mapToPlayer(Set<ZSetOperations.TypedTuple<String>>set){
        Set<Player>playerSet = new HashSet<>();

        for (ZSetOperations.TypedTuple<String> playerTypedTuple: set){
            Player player = playerRepository.getOne(Long.parseLong(playerTypedTuple.getValue()));
            playerSet.add(player);
        }
        return playerSet;
    }
}
