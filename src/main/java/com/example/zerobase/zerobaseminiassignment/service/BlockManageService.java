package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyAuthorityUtil;
import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.model.BlockModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.repository.BlockRepository;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import com.example.zerobase.zerobaseminiassignment.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BlockManageService {

    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;

    public BlockModel saveBlockPost(Long postId) {
        MemberModel blocker = MyJwtUtil.getMember();
        Optional<PostModel> block = postRepository.findById(postId);
        if(block.isEmpty()){
            return null;
        }

        BlockModel blockModel = new BlockModel(blocker,block.get());

        BlockModel result = blockRepository.save(blockModel);

        return result;
    }

    public BlockModel saveBlockMember(Long memberId) {
        MemberModel blocker = MyJwtUtil.getMember();
        Optional<MemberModel> block = memberRepository.findById(memberId);
        if(block.isEmpty()){
            return null;
        }
        BlockModel blockModel =  new BlockModel(blocker, block.get());

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

            if(MyJwtUtil.checkAuth(MyAuthorityUtil.MANAGER)){
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
