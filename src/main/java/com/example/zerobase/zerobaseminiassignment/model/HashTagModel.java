package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@Cacheable
@Getter
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // 맴버 변수에 대한 설정이 동시에 바뀔 가능성은 적다
@Table(indexes = @Index(name = "hashTag_id", columnList = "hashTagId"))
public class HashTagModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hashTagId", nullable = false)
    private Long hashTagId;

    @Column(unique = true)
    private String tagName; // 소문자저장.

    @Column
    private int count = 1; // 태그된 횟수

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public HashTagModel() {}
    public HashTagModel(String tagName) {
        this.tagName = tagName;
    }
    public HashTagModel(String tagName, int count) {
        this.tagName = tagName;
        this.count = count;
    }

    public void updateCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "HashTagModel{" +
                "hashTagId=" + hashTagId +
                ", tagName='" + tagName + '\'' +
                ", count=" + count +
                "} " + super.toString();
    }
}
