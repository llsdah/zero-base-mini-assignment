package com.example.zerobase.zerobaseminiassignment.model;

public class PostModel extends ModificationDateModel{
    private Long postId;
    private Long userId;
    private String title;
    private String contents;
    private int status; // 0:임시, 1:확설화, 2:비활설화, 3:차단

}
