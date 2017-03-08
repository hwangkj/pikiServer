package com.pikitori.mecavo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pikitori.web.repository.CommentDao;
import com.pikitori.web.repository.PictureDao;
import com.pikitori.web.repository.PostDao;
import com.pikitori.web.repository.TagDao;
import com.pikitori.web.vo.CommonVo;
import com.pikitori.web.vo.HeartVo;
import com.pikitori.web.vo.PictureVo;
import com.pikitori.web.vo.PostVo;
import com.pikitori.web.vo.TagVo;
import com.pikitori.web.vo.UserVo;

@Service
public class PostService {

	@Autowired
	private PostDao postDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private PictureDao pictureDao;

	@Autowired
	private TagDao tagDao;

	public void addPost(PostVo postVo) {
		List<PictureVo> pictureList = postVo.getPictureList();
		List<TagVo> tagList = postVo.getTagList();
		
		System.out.println("----------------Service postVo" + postVo);
		System.out.println("----------------Service pictureList" + pictureList);
		System.out.println("----------------Service tagList" + tagList);
		
		// 1. 포스트 저장
		postDao.addPost(postVo);
		// 2. 사진 저장
		pictureDao.addPicture(pictureList);
		// 3. 태그 저장
		tagDao.addTag(tagList);

		System.out.println("----------------Service1 postVo" + postVo);
		System.out.println("----------------Service1 pictureList" + pictureList);
		System.out.println("----------------Service1 tagList" + tagList);

		postDao.addAlbum(postVo);
		postDao.addPostTag(postVo);

	}

	public List<PostVo> showPostList(CommonVo user) {
		List<PostVo> list = postDao.showPostList(user);
		List<PostVo> result = new ArrayList<PostVo>();
		for (PostVo post : list) {
			// 1. commnetList
			post.setCommentList(commentDao.show5ReplyList(post));
			result.add(post);
		}
		return result;
	}

	public PostVo updateHeart(HeartVo heart) {
		return postDao.updateHeart(heart);
	}

	public PostVo updateUnHeart(HeartVo heart) {
		return postDao.updateUnHeart(heart);
	}

	public PostVo getPost(PostVo post) {
		return postDao.getPost(post);
	}

	public boolean updatePost(PostVo post) {
		return postDao.updatepost(post);
	}

	public List<PostVo> postTagList(Long tag_no,Long user_no) {
		List<PostVo> list = postDao.postTagList(tag_no,user_no);
		List<PostVo> result = new ArrayList<PostVo>();
		for(PostVo post : list){
			post.setCommentList(commentDao.show5ReplyList(post));
			result.add(post);
		}
		return result;
	}

	public boolean deltePost(PostVo post) {
		// 1. post_tag삭제
		postDao.deletepostTag(post);
		// 2. comments 삭제
		commentDao.deleteAllbyPost(post);
		// 3. 좋아요 삭제
		postDao.deleteHeartByPost(post);
		// 4. 앨범을 삭제
		pictureDao.deleteAlbum(post);
		// 5. 게시글 삭제
		return postDao.deletepost(post);

	}
	
	public String getTokenFromPostNo(Long post_no){
		return postDao.getTokenFromPostNo(post_no);
	}
	
	public String getTokenFromUserId(String user_id){
		return postDao.getTokenFromUserId(user_id);
	}
	
	public void postPermissionAdd(Long post_no,String request_user_id){
		postDao.postPermissionAdd(post_no, request_user_id);
	}
	public boolean postPermissionCheck(Long post_no, Long user_no){
		
		return postDao.postPermissionCheck(post_no, user_no);
	}
}
