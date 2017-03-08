package com.pikitori.mecavo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pikitori.web.repository.FollowDao;
import com.pikitori.web.repository.UserDao;
import com.pikitori.web.vo.FollowVo;
import com.pikitori.web.vo.UserVo;

@Service
public class FollowService {
	
	@Autowired
	private FollowDao followDao;
	
	@Autowired
	private UserDao userDao;
	
	public boolean following(FollowVo follow){
		return (followDao.following(follow))==1? true :false;
	}
	
	public boolean unfollowing(FollowVo follow){
		return (followDao.unfollowing(follow))==1? true : false;
	}

	
	public boolean followCheck(FollowVo follow){
		return (followDao.followCheck(follow)!=null);
	}
	
	public List<FollowVo> followlist(UserVo user){
		return userDao.followlist(user);
	}
	
	public List<FollowVo> followerlist(UserVo user){
		return userDao.followerlist(user);
	}

	public int updateUserFollowCount(FollowVo follow) {

		return followDao.updateuserCount(follow);
	}

	public int updateUserFrienFollowCount(FollowVo follow) {
		return followDao.updatefriendCount(follow);
	}

	public int updateUserFollowDecount(FollowVo follow) {
		return followDao.updateuserDecount(follow);
		
	}

	public int updateUserFrienFollowDecount(FollowVo follow) {
		return followDao.updatefriendDecount(follow);
	}

//	public void createfollow(FollowVo follow) {
//		return followDao.createFollow(follow);
//	}

}
