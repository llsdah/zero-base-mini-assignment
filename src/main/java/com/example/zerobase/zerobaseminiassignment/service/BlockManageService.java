package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.BlockModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.repository.BlockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BlockManageService {

    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private MemberManageService memberManageService;
    @Autowired
    private PostManageService postManageService;

    public BlockModel saveBlockPost(Long nowMemberId, Long postId) {
        MemberModel blocker = MyJwtUtil.getMember();
        PostModel block = (PostModel) postManageService.find(postId).getData();

        BlockModel blockModel = new BlockModel(blocker,block);
        return blockRepository.save(blockModel);
    }

    public ResultMessageModel saveBlockMember(Long memberId) {
        MemberModel blocker = MyJwtUtil.getMember();
        MemberModel block;
        ResultMessageModel resultMessageModel = memberManageService.find(memberId);
        BlockModel blockModel = null;

        if(resultMessageModel.getData() == null){
            return resultMessageModel;
        }else {
           blockModel = new BlockModel(blocker,(MemberModel) resultMessageModel.getData());
        }

        try {

            blockRepository.save(blockModel);

        } catch (DataIntegrityViolationException e) {
            // 데이터베이스 제약 조건 등에 위배되어 저장 실패
            // 적절한 예외 처리를 수행
            log.error(e.getMessage());
            return ResultMessageUtil.fail();
        }
        return ResultMessageUtil.success();
    }

    public ResultMessageModel findAll() {
        MemberModel memberModel = MyJwtUtil.getMember();

        List<BlockModel> listBlockModel = null;
        try {
            if(memberModel != null){
                listBlockModel = blockRepository.findBlockModelByBlockerMember(memberModel);
            }
            if (listBlockModel == null){
                throw new Exception("listBlockModel is null");
            }
        } catch (Exception e ) {
            log.error(e.getMessage());
            return ResultMessageUtil.fail();
        }

        return ResultMessageUtil.success(listBlockModel);
    }
}
