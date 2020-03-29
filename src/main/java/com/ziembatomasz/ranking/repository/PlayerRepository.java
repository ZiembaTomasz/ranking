package com.ziembatomasz.ranking.repository;

import com.ziembatomasz.ranking.dto.Player;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerRepository extends JpaRepository<Player, Long> {
}
