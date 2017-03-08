package com.pikitori.mecavo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikitori.mecavo.service.TagService;
import com.pikitori.web.vo.TagVo;


@Controller
@RequestMapping("/tag")
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@RequestMapping("/add")
	@ResponseBody
	public String addTag(
			@RequestBody List<TagVo> tagList){

		for(TagVo t: tagList){
			System.out.println(t);	
		}
		
		tagService.addTag(tagList);
		
		return "ok";
	}
	
	@RequestMapping("/searchtag")
	@ResponseBody
	public Object tagPostList(@RequestParam String str, @RequestParam String user_no){
		System.out.println("tagPostList: "+ str + " user_no" + user_no);
		List<TagVo> list = tagService.tagPostList(str,user_no);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", list);
		
		return map;
	}
	
}
