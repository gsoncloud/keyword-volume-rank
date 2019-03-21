package com.sellics.assignment.dto;

public class KeywordDetailDTO {

    private String keyword;
    private Integer score;

    public KeywordDetailDTO(String keyword, Integer score) {
        this.keyword = keyword;
        this.score = score;
    }

    public String getKeyword() {
        return keyword;
    }

    public Integer getScore() {
        return score;
    }
}
