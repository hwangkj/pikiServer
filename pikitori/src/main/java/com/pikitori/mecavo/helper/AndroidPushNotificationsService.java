package com.pikitori.mecavo.helper;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationsService {
	private static final String FIREBASE_SERVER_KEY = "AAAA0MWQx20:APA91bHW-QM-5hdMfjJ07T8cWf2SNG6BZ0M_2B14JYGH9TtZRfiEB90tBXXZD5Za28rG_QRkKHK-GHVSdyWM4Es6UJ5_ajjcEScjEeCgkrduc-EXjzsehCvuA9zl2iKRRtEAcSJgBDKr";

	@Async
	public CompletableFuture<FirebaseResponse> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
//		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json; charset=utf-8"));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json for JSON; application/x-www-form-urlencoded;charset=UTF-8"));		
	
		restTemplate.setInterceptors(interceptors);

		FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", entity,
				FirebaseResponse.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
}
