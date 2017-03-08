package com.pikitori.mecavo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pikitori.mecavo.helper.AndroidPushNotificationsService;
import com.pikitori.mecavo.helper.FirebaseResponse;
import com.pikitori.mecavo.service.PostService;
import com.pikitori.util.JSONResult;
import com.pikitori.web.vo.CommonVo;
import com.pikitori.web.vo.HeartVo;
import com.pikitori.web.vo.PictureVo;
import com.pikitori.web.vo.PostVo;
import com.pikitori.web.vo.UserVo;

@Controller
@RequestMapping("/mecavo/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;

	@RequestMapping("/add")
	@ResponseBody
	public JSONResult addPost(@RequestBody PostVo postVo, HttpSession session) {
		System.out.println("addpost:");

		postService.addPost(postVo);
		return JSONResult.success(postVo);
	}

	@RequestMapping("/showpostList")
	@ResponseBody
	public JSONResult fetchlistPost(@RequestBody CommonVo user, HttpSession session) {
		System.out.println("showpostList: " + user);
		List<PostVo> list = postService.showPostList(user);
		System.out.println("-------------------Controller show " + list);

		return JSONResult.success(list);
	}

	@RequestMapping("/updateHeart")
	@ResponseBody
	public JSONResult updateHeart(@RequestBody HeartVo heart, HttpSession session) {
		System.out.println("updateHeart: " + heart);
		PostVo result = postService.updateHeart(heart);
		if (result == null) {
			JSONResult.error("fail");
		}
		return JSONResult.success(result);
	}

	@RequestMapping("/updateUnHeart")
	@ResponseBody
	public JSONResult updateUnHeart(@RequestBody HeartVo heart, HttpSession session) {
		System.out.println("updateUnHeart: " + heart);
		PostVo result = postService.updateUnHeart(heart);
		if (result == null) {
			JSONResult.error("fail");
		}
		return JSONResult.success(result);
	}

	@RequestMapping("/getPost")
	@ResponseBody
	public JSONResult getPost(@RequestBody PostVo post, HttpSession session) {
		System.out.println("getPost: " + post.getPost_no());
		PostVo result = postService.getPost(post);
		System.out.println("getPost result: " + result);
		if (result == null) {
			JSONResult.error("fail");
		}
		return JSONResult.success(result);
	}

	@RequestMapping("/updatePost")
	@ResponseBody
	public JSONResult updatePost(@RequestBody PostVo post, HttpSession session) {
		System.out.println("updatePost: " + post);
		boolean result = postService.updatePost(post);
		return JSONResult.success(result ? "success" : "fail");
	}

	@RequestMapping("/postTag")
	@ResponseBody
	public Object PostTagList(@RequestParam Long tag_no, @RequestParam Long user_no) {
		System.out.println("postTagsearch: tag_no: "+ tag_no + "user_no:" + user_no );
		List<PostVo> list = postService.postTagList(tag_no,user_no);
		for (PostVo p : list) {
			System.out.println("PostTagList1" + p);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", list);

		return map;
	}

	@RequestMapping("/deletePost")
	@ResponseBody
	public JSONResult deletePost(@RequestBody PostVo post, HttpSession session) {
		boolean result = postService.deltePost(post);
		return JSONResult.success(result ? "success" : "fail");
	}

	@RequestMapping("/modifyPermissionRequest")
	@ResponseBody
	public ResponseEntity<String> modifyPermissionRequest(
			@RequestParam String text, 
			@RequestParam Long post_no,
			@RequestParam String user_id,
			HttpServletResponse response
			) {
		System.out.println("----------왔따");
		System.out.println("Text = " + text + " Post_no = "+post_no);
		
		String token = postService.getTokenFromPostNo(post_no);
		
		System.out.println("token:" + token);
		
		response.setContentType("application/json; charset=UTF-8");
		
		JSONObject body = new JSONObject();
		// JsonArray registration_ids = new JsonArray();
		// body.put("registration_ids", registration_ids);
		body.put("to", token);
		body.put("priority", "high");
		// body.put("dry_run", true);

		JSONObject notification = new JSONObject();
		notification.put("body", text);
		notification.put("title", "Post Permission Request");
		// notification.put("icon", "myicon");

		JSONObject data = new JSONObject();
		data.put("post_no", post_no);
		data.put("request_user_id", user_id);

		body.put("notification", notification);
		body.put("data", data);
	

		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			FirebaseResponse firebaseResponse = pushNotification.get();
			if (firebaseResponse.getSuccess() == 1) {
				// log.info("push notification sent ok!");
			} else {
				// log.error("error sending push notifications: " +
				// firebaseResponse.toString());
			}
			return new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("the push notification cannot be send.", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/postPermissionRequestAccept")
	@ResponseBody
	public ResponseEntity<String> postPermissionRequestAccept(
			@RequestParam int status, 
			@RequestParam Long post_no,
			@RequestParam String request_user_id
			) {
		
		System.out.println(status);
		System.out.println(post_no);
		System.out.println(request_user_id);
		String token = postService.getTokenFromUserId(request_user_id);
		if(status==-1){
			postService.postPermissionAdd(post_no,request_user_id);
		}
		
		
		JSONObject body = new JSONObject();
		// JsonArray registration_ids = new JsonArray();
		// body.put("registration_ids", registration_ids);
		body.put("to", token);
		body.put("priority", "high");
		// body.put("dry_run", true);

		JSONObject notification = new JSONObject();
		if(status==-2){
			notification.put("body", "Rejected");
		}else{
			notification.put("body", "Authorized");
		}
		notification.put("title", "Post Permission Request");
		// notification.put("icon", "myicon");

		//JSONObject data = new JSONObject();
		//data.put("post_no", post_no);
		//data.put("request_user_id", user_id);

		body.put("notification", notification);
		//body.put("data", data);

		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			FirebaseResponse firebaseResponse = pushNotification.get();
			if (firebaseResponse.getSuccess() == 1) {
				// log.info("push notification sent ok!");
			} else {
				// log.error("error sending push notifications: " +
				// firebaseResponse.toString());
			}
			return new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("the push notification cannot be send.", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/postPermissionCheck")
	@ResponseBody
	public JSONResult postPermissionCheck(
			@RequestParam Long post_no,
			@RequestParam Long user_no){
		System.out.println(post_no);
		System.out.println(user_no);
		
		return JSONResult.success(postService.postPermissionCheck(post_no, user_no));
	}
}
