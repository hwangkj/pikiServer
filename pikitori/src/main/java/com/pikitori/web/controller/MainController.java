package com.pikitori.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikitori.mecavo.service.UserService;
import com.pikitori.web.vo.UserVo;

@Controller
@RequestMapping("/")
public class MainController {

//	@RequestMapping("")
//	public String index(HttpSession session) {
//		session.setAttribute("authUser", "test");
//		return "/main/index";
//	}

	@Autowired
	UserService userService;

	@RequestMapping("/list")
	@ResponseBody
	public Object checkEmail(HttpSession session) {

		String authUser = (String) session.getAttribute("authUser");
		System.out.println(authUser);

		List<UserVo> list = userService.getAllUsers();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", list);

		return map;
	}

	@RequestMapping("/get")
	public String getUser(@RequestParam String name, @RequestParam String email) {
		System.out.println(email);
		System.out.println(name);

		return null;

	}

	// @Autowired
	// FileIUploadService fileIUploadService;
	//
	// @RequestMapping("/fileupload")
	// @ResponseBody
	// public String fileupload(@RequestParam List<MultipartFile> file ,
	// @RequestParam String comment){
	// System.out.println(comment);
	// for(Object f: file){
	// System.out.println(((MultipartFile)f).getOriginalFilename());
	// }
	//
	// try {
	// fileIUploadService.makemovie(file);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return "ok";
	//
	// }

//	@ResponseBody
//	@RequestMapping("/pictures")
//	public String getPictures(@ModelAttribute Picture name) {
//		
//		System.out.println(name);
//
//		return "pictures";
//	}
//	
//	@ResponseBody
//	@RequestMapping("/pictures1")
//	public String getPictures1(@RequestParam List<Picture> names) {
//		
//		for(Picture p : names){
//			System.out.println(p);
//		}
//
//		return "pictures";
//	}
}
