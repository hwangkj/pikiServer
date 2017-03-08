package com.pikitori.util;

import org.springframework.stereotype.Repository;

@Repository
public class Property {

	public static boolean isdev = true;
	public static final String ffmpegPath = "/Users/admin/Downloads/source/ffmpeg-win64/ffmpeg/bin/ffmpeg.exe";
	public static final String server_ffmpegPath = "/usr/bin/ffmpeg";

//	public static String domain = "http://192.168.1.48:8088/facebookSite1";
	public static String domain = "http://192.168.1.4:8080/pikitori";
	
	public static String server_domain = "http://www.pikitori.com/pikitori" ;
	
	public static String image = "/piki/img";
	public static String movie = "/piki/mp4";
	
//	private static final String SAVE_PIKI_IMAGE = "D:\\piki";
//	private static final String SAVE_TMP = "D:\\piki_tmp";
//	private static final String SAVE_MOVIE = "D:\\piki_movie";
	
	public static final String SAVE_PIKI_IMAGE = "/piki";
	public static final String SAVE_TMP = "/piki_tmp";
	public static final String SAVE_MOVIE = "/piki_movie";
}
