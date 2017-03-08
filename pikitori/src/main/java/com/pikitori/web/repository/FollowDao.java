package com.pikitori.web.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pikitori.web.vo.FollowVo;

@Repository
public class FollowDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int following(FollowVo follow){
		return sqlSession.insert("follow.following", follow);
	}
	
	public int unfollowing(FollowVo follow){
		return sqlSession.delete("follow.unfollowing", follow);
	}
	
	public FollowVo followCheck(FollowVo follow){
		return sqlSession.selectOne("follow.followCheck", follow);
	}

	public int updateuserCount(FollowVo follow) {
		return sqlSession.update("follow.userCount",follow);
	}
	public int updatefriendCount(FollowVo follow) {
		return sqlSession.update("follow.userFriendCount",follow);
	}

	public int updateuserDecount(FollowVo follow) {
		return sqlSession.update("follow.userDecount",follow);
	}

	public int updatefriendDecount(FollowVo follow) {
		return sqlSession.update("follow.userFriendDecount",follow);
	}
	
}
