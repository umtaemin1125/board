package com.study.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //해당 어너테이션(@)을 보고 JPA가 읽어서 처리해줌.
@Data

public class Board {

    @Id //PK 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY : 마리아DB , SEQUENCE : 오라클 AUTO : 알아서 지정

    private Integer id;

    private String title;

    private String content;

    private String filename;

    private String filepath;
}
