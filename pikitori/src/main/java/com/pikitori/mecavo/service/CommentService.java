package com.pikitori.mecavo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pikitori.web.repository.CommentDao;
import com.pikitori.web.vo.CommentVo;
import com.pikitori.web.vo.PostVo;

@Service
public class CommentService {
	@Autowired
	private CommentDao commentDao;
	
	public List<CommentVo> showReplyList(PostVo post){
		return commentDao.showReplyList(post);
	}
	
	public List<CommentVo> show5ReplyList(PostVo post){
		return commentDao.show5ReplyList(post);
	}
	
	public boolean addReply(CommentVo postReply){
		boolean result = commentDao.addReply(postReply);
		if(result){
			commentDao.updateUPCommentCount(postReply);
		}
		return result;
	}

	public boolean deleteComment(CommentVo comment) {
		boolean result = commentDao.deleteComment(comment);
		if(result){
			commentDao.updateDownCommentCount(comment);
		}
		return result;
	}
}
