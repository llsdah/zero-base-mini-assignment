package com.example.zerobase.zerobaseminiassignment.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // 맴버 변수에 대한 설정이 동시에 바뀔 가능성은 적다
@Table(indexes = @Index(name = "hashTag_id", columnList = "hashTagId"))
public class HashTagModel extends RegistrationDateModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hashTagId", nullable = false)
    private Long hashTagId;

    @Column(unique = true)
    private String tagName; // 소문자저장.

    @Column(columnDefinition = "int default 1")
    private int count; // 태그된 횟수

    public HashTagModel() {}
    public HashTagModel(String tagName) {
        this.tagName = tagName;
    }
    public HashTagModel(String tagName, int count) {
        this.tagName = tagName;
        this.count = count;
    }


    public Long getHashTagId() { return hashTagId;}
    public String getTagName() {
        return tagName;
    }

    public void updateCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
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
