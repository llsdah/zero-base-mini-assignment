package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyAuthUtil;
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

    public BlockModel saveBlockPost(Long postId) {
        MemberModel blocker = MyJwtUtil.getMember();
        PostModel block = postManageService.find(postId);

        BlockModel blockModel = new BlockModel(blocker,block);

        BlockModel result = blockRepository.save(blockModel);

        return result;
    }

    public BlockModel saveBlockMember(Long memberId) {
        MemberModel blocker = MyJwtUtil.getMember();
        MemberModel block = memberManageService.find(memberId);

        BlockModel blockModel =  new BlockModel(blocker, block);

        try {
            blockRepository.save(blockModel);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
        }

        return blockModel;
    }

    // 관리자일 경우 전체 조회
    public List<BlockModel> findAll() {

        List<BlockModel> listBlockModel = null;
        try {

            if(MyJwtUtil.checkAuth(MyAuthUtil.MANAGER)){
                listBlockModel = blockRepository.findAll();
            }else{
                listBlockModel = blockRepository.findBlockModelByBlockerMember(MyJwtUtil.getMember());
            }

            if (listBlockModel == null){
                throw new Exception("listBlockModel is null");
            }

        } catch (Exception e ) {
            log.error(e.getMessage());
        }

        return listBlockModel;
    }

    public boolean delete(Long blockId) {
        try{
            blockRepository.deleteById(blockId);
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

        return true;
    }
    public boolean deleteByBlockerMember(MemberModel blockerMember) {
        try{
            blockRepository.deleteByBlockerMember(blockerMember);
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

        return true;
    }


}
