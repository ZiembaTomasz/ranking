package com.ziembatomasz.ranking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Player {
    private String name;
    private String userId;
    private Double score;
}
