package com.example.QuizApp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LeaderBoardEntry {
    private String username;
    private int score;
}
