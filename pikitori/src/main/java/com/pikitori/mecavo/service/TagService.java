package com.pikitori.mecavo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pikitori.web.repository.TagDao;
import com.pikitori.web.vo.TagVo;

@Service
public class TagService {
	
	@Autowired
	private TagDao tagDao;
	
	public void addTag(List<TagVo> tagList){
		tagDao.addTag(tagList);
	}
	
	public List<TagVo> tagPostList(String str, String user_no){
		return tagDao.tagPostList(str,user_no);
	}

}
