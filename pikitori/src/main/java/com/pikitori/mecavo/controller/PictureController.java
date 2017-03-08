package com.pikitori.mecavo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pikitori.mecavo.service.FileUploadService;
import com.pikitori.mecavo.service.PictureService;
import com.pikitori.util.FileUtils;
import com.pikitori.util.JSONResult;
import com.pikitori.web.vo.PictureVo;
import com.pikitori.web.vo.PostVo;

@Controller
@RequestMapping("/mecavo/picture")
public class PictureController {
	
	@Autowired
	private PictureService pictureService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private FileUtils utils;
	
	@RequestMapping("/makemovie")
	@ResponseBody
	public JSONResult makeMovie(
			@RequestParam List<MultipartFile> fileList,
			@RequestParam List<Long> pictureNoList,
			@RequestParam List<String> pictureType,
			@RequestParam Long postNo,
			@RequestParam Long userNo,
			@RequestParam double speed) throws IOException{
		
		for(MultipartFile f: fileList){
			System.out.println(f.getOriginalFilename());
		}
		System.out.println(pictureNoList);
		System.out.println(postNo);
		System.out.println("speed:" +speed);
		
		boolean result = fileUploadService.makeMovie(fileList, pictureNoList,pictureType, postNo,userNo, speed);
		return JSONResult.success(result ? "create" : "error");
	}
	
	@RequestMapping("/getpictureuseingmap")
	@ResponseBody
	public Object getPictureUseMap(
			@RequestParam Long user_no
			){
		PictureVo pictureVo = new PictureVo();
		pictureVo.setUser_no(user_no);
		
		System.out.println(pictureVo);
		List<PictureVo> list = pictureService.getPictureUseingMap(pictureVo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", list);
		
		System.out.println(map.get("data"));
	
		
		return map;
	}

	@RequestMapping("/getpicturegallery")
	@ResponseBody
	public JSONResult fetchGallery(@RequestBody PostVo post){
		System.out.println("getpicturegallery: " + post);
		List<PictureVo> list = pictureService.getPictureGallery(post);
		return JSONResult.success(list);
	}
}
