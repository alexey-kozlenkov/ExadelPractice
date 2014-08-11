package com.exadel.studbase.domain.enumeration;

/**
 * Created by ala'n on 11.08.2014.
 */
public enum EnglishLevel {
    Beginner(0),
    Elementary(1),
    Pre_Intermediate(2),
    Intermediate(3),
    Upper_Intermediate(4),
    Advanced(5);

    int level;

    EnglishLevel(int level){
        this.level = level;
    }
}
