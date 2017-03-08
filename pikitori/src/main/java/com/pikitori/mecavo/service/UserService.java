package com.pikitori.mecavo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pikitori.web.repository.UserDao;
import com.pikitori.web.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public List<UserVo> getAllUsers() {
		List<UserVo> list = userDao.getList();
		return list;
	}

	public boolean checkEmail(String user_id) {
		return (userDao.getEmail(user_id) != null);
	}

	public boolean checkNickname(String user_name) {
		return (userDao.getNickname(user_name) != null);
	}

	public UserVo login(UserVo user) {
		if(user.getUser_password()== null || "".equals(user.getUser_password())){
			return userDao.LoginbyIndex(user);
		}
		return userDao.Login(user);
	}

	public boolean addUser(UserVo user) {
		return userDao.addUser(user);
	}

	public UserVo addF_User(UserVo user) {
		if(userDao.isExistF_User(user) == null){
			userDao.addF_User(user);
		}
		return userDao.isExistF_User(user);
	}

//	public boolean updateProfileImg(String filepath, UserVo user) {
//		return userDao.updateProfileImg(filepath, user) == 1 ;
//	}

	public UserVo updateProfile(UserVo user) {
		return userDao.updateProfile(user);
	}

	public UserVo getUser(Long user_no) {
		return userDao.getUser(user_no);
	}
	
	public List<UserVo> searchNameList(String str, Long user_no) {
		return userDao.searchNameList(str,user_no);
	}

	
	////////////////////////////////////////
	public UserVo webLogin(UserVo user){
		return userDao.webLogin(user);
	}
	
	public String tokenCheck(Long user_no, String token){
		return userDao.tokenCheck(user_no,token);
	}
	


}
