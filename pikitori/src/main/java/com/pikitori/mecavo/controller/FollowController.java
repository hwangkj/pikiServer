package com.pikitori.mecavo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikitori.mecavo.service.FollowService;
import com.pikitori.util.JSONResult;
import com.pikitori.web.vo.FollowVo;
import com.pikitori.web.vo.UserVo;

@Controller
@RequestMapping("/mecavo/follow")
public class FollowController {

	@Autowired
	private FollowService followService;

//	@RequestMapping("createfollow")
//	@ResponseBody
//	public String createfollow(@RequestBody FollowVo follow) {
//		System.out.println("createfollow" + follow);
//		followService.createfollow(follow);
//		return "";
//	}
	
	@RequestMapping("following")
	@ResponseBody
	public JSONResult following(@RequestBody FollowVo follow) {
		System.out.println("following: "+ follow);
		boolean result = followService.following(follow);
		
		if(result){
			 followService.updateUserFollowCount(follow);
			 followService.updateUserFrienFollowCount(follow);
		}
		return JSONResult.success(result ? "create" : "error");
	}

	@RequestMapping("followlist")
	@ResponseBody
	public Object followlist(@RequestBody UserVo user, HttpSession session) {
		System.out.println("followlist: " + user);
		System.out.println(session.getId());
		List<FollowVo> list = followService.followlist(user);
		for(FollowVo v: list){
			System.out.println("follow list: "+ v);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", list);

		return map;
	}

	@RequestMapping("followerlist")
	@ResponseBody
	public Object followerlist(@RequestBody UserVo user, HttpSession session) {
		System.out.println("followerlist: " + user);
		System.out.println(session.getId());
		List<FollowVo> list = followService.followerlist(user);
		for(FollowVo v: list){
			System.out.println("follower list: "+ v);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", list);

		return map;
	}

	@RequestMapping("unfollowing")
	@ResponseBody
	public JSONResult unfollowing(@RequestBody FollowVo follow) {
		boolean result = followService.unfollowing(follow);
		if(result){
			 followService.updateUserFollowDecount(follow);
			 followService.updateUserFrienFollowDecount(follow);
		}
		return JSONResult.success(result ? "unfollowed" : "fail");
	}

	@RequestMapping("/followcheck")
	@ResponseBody // json형식으로 출력할때 필요하다.
	public JSONResult followCheck(@RequestBody FollowVo follow) {
		System.out.println(follow.toString());
		boolean result = followService.followCheck(follow);
		return JSONResult.success(result ? "follow" : "unfollow");
	}

}
