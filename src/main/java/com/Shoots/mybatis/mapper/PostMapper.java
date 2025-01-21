package com.Shoots.mybatis.mapper;

import com.Shoots.domain.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/*

*/
@Mapper
public interface PostMapper {

    // 글의 갯수 구하기
    public int getListCount(HashMap<String, Object> map);

    public List<Post> getPostList(HashMap<String, Object> map);

    public List<Post> getAdminPostList(HashMap<String, Object> map);

    // 글 등록하기
    public void insertPost(Post post);

    // 조회수 업데이트
    public int setReadCountUpdate(int num);

    // 글쓴이인지 확인
    public Post isPostWriter(HashMap<String, Object> map);

    Post getDetail(int num);

    // 글 수정
    public int postModify(Post modifypost);

    // 글 삭제
    public int postDelete(Post post);

    public List<String> getDeleteFileList();

    public void deleteFileList(String filename);

    public int getAdminListCount(HashMap<String, Object> map);

    public List<Post> getMyPostList(int id);

    public int getMyPostListCount(int id);

    // completed >> available
    public void setAvailable(int post_idx);

    // available >> completed
    public void setCompleted(int post_idx);
}
