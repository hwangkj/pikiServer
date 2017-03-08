package com.pikitori.mecavo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikitori.mecavo.service.CommentService;
import com.pikitori.util.JSONResult;
import com.pikitori.web.vo.CommentVo;
import com.pikitori.web.vo.PostVo;

@Controller
@RequestMapping("/mecavo/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@RequestMapping("/showreplyList")
	@ResponseBody
	public Object showReplyList(@RequestBody PostVo post) {
		List<CommentVo> list = commentService.showReplyList(post);
		System.out.println(post.getPost_no());
		System.out.println(post.getUser_no());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", list);
		System.out.println(map.get("data"));
		return map;

	}

	@RequestMapping("/addreply")
	@ResponseBody
	public JSONResult addReply(@RequestBody CommentVo comment) {
		System.out.println("addreply:"+ comment);
		boolean result = commentService.addReply(comment);
		return JSONResult.success(result ? "success" : "fail" );
	}
	
	@RequestMapping("/deleteComment")
	@ResponseBody
	public JSONResult deleteComment(@RequestBody CommentVo comment) {
		System.out.println("deleteComment: "+ comment);
		boolean result = commentService.deleteComment(comment);
		return JSONResult.success(result ? "success" : "fail" );
	}
}
