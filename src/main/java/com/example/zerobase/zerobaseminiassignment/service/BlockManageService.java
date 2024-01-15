package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.BlockModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockManageService {

    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private MemberManageService memberManageService;
    @Autowired
    private PostManageService postManageService;

    public BlockModel saveBlockPost(Long nowMemberId, Long postId) {
        MemberModel blocker = memberManageService.find(nowMemberId);
        PostModel block = postManageService.find(postId);

        BlockModel blockModel = new BlockModel(blocker,block);
        return blockRepository.save(blockModel);
    }

    public BlockModel saveBlockMember(Long nowMemberId, Long memberId) {
        MemberModel blocker = memberManageService.find(nowMemberId);
        MemberModel block = memberManageService.find(memberId);

        BlockModel blockModel = new BlockModel(blocker,block);
        return blockRepository.save(blockModel);

    }

    public List<BlockModel> findAll(Long nowMemberId) {
        MemberModel memberModel = memberManageService.find(nowMemberId);
        return blockRepository.findBlockModelByBlockerMember(memberModel);
    }
}
