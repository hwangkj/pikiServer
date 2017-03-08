package com.pikitori.web.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pikitori.web.vo.CommonVo;
import com.pikitori.web.vo.HeartVo;
import com.pikitori.web.vo.PostVo;
import com.pikitori.web.vo.UserVo;

@Repository
public class PostDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void addPost(PostVo postVo){
		sqlSession.insert("post.addPost",postVo);
	}
	
	public void addAlbum(PostVo postVo){
		Map<String,Long> map = new HashMap<String,Long>();
		for(int i = 0; i<postVo.getPictureList().size();i++){
			map.put("post_no", postVo.getPost_no());
			map.put("picture_no",postVo.getPictureList().get(i).getPicture_no());
			sqlSession.insert("post.addAlbum",map);
		}
	}
	
	
	public void addPostTag(PostVo postVo){
		Map<String,Long> map = new HashMap<String,Long>();
		for(int i = 0; i<postVo.getTagList().size(); i++){
			map.put("post_no", postVo.getPost_no());
			map.put("tag_no", postVo.getTagList().get(i).getTag_no());
			sqlSession.insert("post.addPostTag",map);
		}
	}
	
	public boolean updatePostMovie(Long postNo, Long userNo, String movieurl, boolean success){
		if(success){
			//1. post  update
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("post_no", postNo);
			map.put("post_movie", movieurl);
			map.put("user_no", userNo);
			int count  = sqlSession.update("post.updateMovieUrlsuccess",map);
			
			//2. post count + 
			if( count > 0 ){
				sqlSession.update("post.updatePostUpCount",map);
			}
			return (count>0)? true : false;
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("post_no", postNo);
			map.put("post_movie", movieurl);
			int count = sqlSession.delete("post.updateMovieUrlfail_postTag",map);
			count = sqlSession.delete("post.updateMovieUrlfail_Album",map);
			count  = sqlSession.delete("post.updateMovieUrlfail_post",map);
			return (count>0)? true : false;
		}
	}
	
	public List<PostVo> showPostList(CommonVo user){
		if( user.isPost_ispublic()){
			return sqlSession.selectList("post.showpostList",user);		
		}else{
			return sqlSession.selectList("post.showpostPublicList",user);			
		}
	}

	public PostVo updateHeart(HeartVo heart) {
		if( sqlSession.insert("post.createHeart",heart) >0 ){
			 sqlSession.update("post.updateHeart", heart);
			 return sqlSession.selectOne("post.getPostWithHeart", heart);
		}
		return null;
	}

	public PostVo updateUnHeart(HeartVo heart) {
		if( sqlSession.delete("post.deleteHeart",heart) >0 ){
			sqlSession.update("post.updateUnHeart", heart);
			return sqlSession.selectOne("post.getPostWithHeart", heart);
		}
		return null;
	}
	
	public boolean deleteHeartByPost(PostVo post){
		System.out.println("deleteHeartByPost"+ post.getPost_no());
		return sqlSession.delete("post.deleteAllHeartbyPost",post) >0 ? true:false;
	}

	public PostVo getPost(PostVo post) {
		return sqlSession.selectOne("post.getPost",post);
	}

	public boolean updatepost(PostVo post) {
		return ( sqlSession.update("post.updatePost",post) > 0) ?true: false;
	}
	
	public List<PostVo> postTagList(Long tag_no, Long user_no){
		Map map = new HashMap();
		map.put("tag_no", tag_no);
		map.put("user_no", user_no);
		return sqlSession.selectList("post.postTagList", map);
	}

	public boolean deletepost(PostVo post) {
		System.out.println("deletepost"+ post.getPost_no());
		return ( sqlSession.update("post.deletePost",post) > 0) ? true: false;
	}

	public boolean deletepostTag(PostVo post) {
		System.out.println("deletepostTag"+ post.getPost_no());
		return ( sqlSession.update("post.deletePostTag",post) > 0) ? true: false;
	}
	
	public String getTokenFromPostNo(Long post_no){
		return sqlSession.selectOne("post.getTokenFromPostNo",post_no);
	}
	
	public String getTokenFromUserId(String user_id){
		return sqlSession.selectOne("post.getTokenFromUserId",user_id);
	}
	
	public void postPermissionAdd(Long post_no, String request_user_id){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("post_no", post_no);
		map.put("request_user_id", request_user_id);
		sqlSession.insert("post.postPermissionAdd",map);
	}

	public boolean postPermissionCheck(Long post_no, Long user_no){
		Map<String, Object> map  = new HashMap<>();
		map.put("post_no", post_no);
		map.put("user_no", user_no);
		return sqlSession.selectOne("post.postPermissionCheck", map)!=null;
	}
}
