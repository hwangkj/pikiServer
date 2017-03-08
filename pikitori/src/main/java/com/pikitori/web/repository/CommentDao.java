package com.pikitori.web.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pikitori.web.vo.CommentVo;
import com.pikitori.web.vo.PostVo;

@Repository
public class CommentDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<CommentVo> showReplyList(PostVo post){
		return sqlSession.selectList("comment.showreplyList",post);
		
	}
	
	public List<CommentVo> show5ReplyList(PostVo post){
		return sqlSession.selectList("comment.show5ReplyList",post);
		
	}
	public boolean addReply(CommentVo postReply){
		return sqlSession.insert("comment.addReply", postReply) > 0 ? true : false;
	}

	public int updateUPCommentCount(CommentVo postReply) {
		return sqlSession.update("comment.updateCount", postReply);
	}
	
	public boolean deleteAllbyPost(PostVo post){
		System.out.println("deleteAllbyPost"+ post.getPost_no());
		return sqlSession.delete("comment.deleteAllbyPost",post) > 0? true: false;
	}

	public boolean deleteComment(CommentVo comment) {
		return sqlSession.delete("comment.deleteComment", comment) >0 ? true: false;
	}

	public int updateDownCommentCount(CommentVo comment) {
		return sqlSession.update("comment.deleteCount", comment);
	}
}
