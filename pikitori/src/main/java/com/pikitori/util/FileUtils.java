package com.pikitori.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Random;

import javax.imageio.ImageIO; 

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Repository
public class FileUtils {
	
	private SecureRandom random = new SecureRandom();
	private Property property = new Property();
	
	public String createImageDir(String path, String saveFileName) {
		System.out.println("createImageDir: " + saveFileName);
		int location = saveFileName.lastIndexOf('.');
		String first = saveFileName.substring(location -1 ,location);
		String second = saveFileName.substring(location -2 ,location-1);
		File firstdir = new File(path,first);
		if(! firstdir.exists()){
			firstdir.mkdirs();
		}
		File seconddir = new File(firstdir,second);
		if(!seconddir.exists() ){
			seconddir.mkdirs();
		}  
//		saveFileName = first+File.separator+second+File.separator+saveFileName;
		saveFileName = first+"/"+second+"/"+saveFileName;
		return saveFileName;
	}
	/*video image */
	public String writeFile(MultipartFile multipartFile, String path, String saveFileName) throws IOException {
		
		byte[] fileData = multipartFile.getBytes();
//		String dirpath = SAVE_PATH + File.separator + saveFileName;
		String dirpath = path + "/" + saveFileName;
		
		File dir = new File(path);
		System.out.println("file exist? "+ dir.exists());
		if(!dir.exists()){
			dir.mkdir();
		}

		BufferedImage bi = ImageIO.read(new ByteArrayInputStream(fileData));
		ImageIO.write(bi, "png", new File(dirpath)); 
		System.out.println("writed File");		
		return (saveFileName != null) ? saveFileName: "" ;
	}
	/*profile image */
	public String writeThumbFile(MultipartFile multipartFile, String path, String saveFileName) throws IOException {
		byte[] fileData = multipartFile.getBytes();
		saveFileName = saveFileName.substring(0,saveFileName.lastIndexOf('.'));
//		String dirpath = SAVE_PATH + File.separator + saveFileName;
		String dirpath = path + "/" + saveFileName+".png";
		String thumb_dirpath = path + "/" + saveFileName+"_thumb.png";
		saveFileName+=".png";
		
		File dir = new File(path);
		System.out.println("file exist? "+ dir.exists());
		if(!dir.exists()){
			dir.mkdir();
		}

		BufferedImage bi = ImageIO.read(new ByteArrayInputStream(fileData));
		//1. thumb 생성.
		int thumbnail_width = 100;
		int thumbnail_height = (bi.getHeight()*thumbnail_width)/bi.getWidth();
		
		BufferedImage thumb_bi = new BufferedImage(thumbnail_width,thumbnail_height,BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphic = thumb_bi.createGraphics();
		graphic.drawImage(bi, 0, 0, thumbnail_width, thumbnail_height, null);
		ImageIO.write(bi, "png", new File(dirpath));
		ImageIO.write(thumb_bi, "png", new File(thumb_dirpath)); 
		System.out.println("writed File" + "thumb height:" + thumbnail_height);		
		return (saveFileName != null) ? saveFileName: "" ;
	}
	
	public String generateSaveFileName(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		
		fileName += new BigInteger(64, random).toString(32);
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName);

		return fileName;
	}
	
	public String generateSaveFileNameNoExt(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		
		fileName += new BigInteger(64, random).toString(32);
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName);
		return fileName;
	}
	
//	public String convert_old(String tmppath, String resultpath, double speed){
//		String filename = generateSaveFileName("mp4");
//		String fResult = resultpath+"/"+filename;
//		
//		File dir = new File(resultpath);
//		if(!dir.exists()){
//			dir.mkdir();
//		}
//		
////		String fOriginal = SAVE_TMP_PATH + File.separator+"image%00d"+".png"; // 실시간으로 업로드되는 파일
//		String fOriginal = tmppath +"/"+"image%00d"+".png"; // 실시간으로 업로드되는 파일
//		String [] cmdLine_resize = new String[]{
//				"mogrify",
//				"-extent",
//				"1024x768",
//				"-flatten",
//				"-background",
//				"black",
//				"-gravity",
//				"center",
//				"-format",
//				"png",
//				tmppath+"/"+"*.png"
//		};
//		String[] cmdLine = new String[] {
//				ffmpegPath, 
//				"-framerate",
//				String.valueOf(1/speed),
//				"-f",
//				"image2",
//				"-s", "1024x768",
//				"-i", fOriginal,
//				"-q:v","0",
//				"-vcodec", "libx264",
//				"-crf", "0",
//				"-pix_fmt", "yuv420p",  
//				fResult ,"-y"};
//		
////		String[] cmdLine = new String[] {
////				"/usr/bin/ffmpeg", 
////				"-framerate",
////				String.valueOf(speed),
////				"-f",
////				"image2",
////				"-s", "1024x768",
////				"-i", fOriginal,
////				"-q:v","0",
////				"-vcodec", "libx264",
////				"-crf", "0",
////				"-pix_fmt", "yuv420p",  
////				fResult ,"-y"};
//		
//		try {
//			byCommonsExec(cmdLine_resize);
//			byCommonsExec(cmdLine);
//		} catch (IOException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return filename;
//	}
	
	public String convertMp4(String[] imageList,String tmppath, String resultpath, double speed){
		long start = System.currentTimeMillis();
		//1. tmp 파일 mogrify
		mogry(tmppath);
		//2. tmp 파일 *25
		makeframe(tmppath,imageList,speed);
		
		//setting 		
		String[] music = new String[]{
				resultpath+File.separator + "music"+File.separator+"1.mp3",
				resultpath+File.separator + "music"+File.separator+"2.mp3",
				resultpath+File.separator + "music"+File.separator+"3.mp3",
				resultpath+File.separator + "music"+File.separator+"4.mp3",
				resultpath+File.separator + "music"+File.separator+"5.mp3",
				resultpath+File.separator + "music"+File.separator+"6.mp3",
				resultpath+File.separator + "music"+File.separator+"7.mp3"};
		Random r = new Random();
		int track = r.nextInt(music.length);
		int frame = imageList.length;
		double tempo = 1.0;
		
		//setting audio point 
		String point = makepoint(music[track],frame);
		
		//3. ffmpeg
		String filename = ffmpeg(tmppath,resultpath,music,track,frame,tempo,point,speed);
		long end = System.currentTimeMillis();
		long gap = end - start;
		System.out.println(" complete time: "+ gap/1000 + "초");
		return filename;
	}

	public String ffmpeg(String tmppath,String resultpath,String[] music, int track, int frame, double tempo, String point,double speed){
		String fOriginal = tmppath+"/img%000d.png"; // 실시간으로 업로드되는 파일
		String filename = generateSaveFileName("mp4");
		String fResult = resultpath+File.separator+filename;
		String[] cmdLine ;
		if(property.isdev){
			 cmdLine = new String[] {
					property.ffmpegPath,
					"-f", "image2",
					"-s", "xga",
					"-i ", fOriginal,
					"-ss", point,
					"-t", String.valueOf(frame*speed),
					"-i ", music[track],
					"-af ","atempo="+String.valueOf(tempo),
					"-q:v","0",
					"-vcodec ", "libx264",
					"-preset ", "slow",
					"-pix_fmt", "yuv420p",
					"-level ", "3.0",
					fResult ,"-y"};	
		}else{
			 cmdLine = new String[] {
						property.server_ffmpegPath,
						"-thread_queue_size","10240",
						"-f", "image2",
						"-s", "xga",
						"-i ", fOriginal,
						"-ss", point,
						"-t", String.valueOf(frame),
						"-i ", music[track],
						"-af ","atempo="+String.valueOf(tempo),
						"-q:v","0",
						"-vcodec ", "libx264",
						"-preset ", "slow",
						"-pix_fmt", "yuv420p",
						"-level ", "3.0",
						"-strict","-2",
						fResult ,"-y"};		
		}
		try {
			byCommonsExec(cmdLine);
			System.out.println("ffmpeg complete");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename;
	}
	
	private String makepoint(String fileLocation, int frame ) {
		Random r = new Random();
		try {
		        InputStream input = new FileInputStream(new File(fileLocation));
		        DefaultHandler handler = new DefaultHandler();
		        Metadata metadata = new Metadata();
		        Parser parser = new Mp3Parser();
		        ParseContext parseCtx = new ParseContext();
		        parser.parse(input, handler, metadata, parseCtx);
		        input.close();
		
		        String s = metadata.get("xmpDM:duration");
		        int abs = (int) Math.abs(Float.parseFloat(s));
				int point = r.nextInt(abs);
		        String duration = String.format("%02d:%02d:%02d", 0 ,abs/60000 ,(abs%60000)/1000 );
		        String point_start = String.format("%02d:%02d:%02d", 0 ,point/60000 ,(point%60000)/1000 );
		        System.out.println("total duration : "+ duration);
		        System.out.println("point start : "+ point_start);
		        return point_start;
	        } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        } catch (IOException e) {
	        e.printStackTrace();
	        } catch (SAXException e) {
	        e.printStackTrace();
	        } catch (TikaException e) {
	        e.printStackTrace();
	        }
		return "-1";
	}

	private static final int fps = 25;
	public boolean makeframe(String tmppath,String[] imageList,double speed){
		int value = (int) (fps * speed);
		try {
			int k = 0;
			for( int t= 0; t < imageList.length ; t++){
				System.out.println("file copy:" + imageList[t]);
				File file = new File(imageList[t]);
				BufferedImage i = ImageIO.read(file); // 변형하고자 하는 이미지
				for( k = t*value; k <( value * (t+1)); k++){
					File tmp = new File(tmppath+"/img"+k+".png");
					tmp.createNewFile();
					ImageIO.write(i, "png", tmp); // 변형하고자 하는 이미지
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public void mogry(String tmppath){

		String [] cmdLine_resize = new String[]{
				"mogrify",
				"-extent",
				"1024x768",
				"-flatten",
				"-background",
				"black",
				"-gravity",
				"center",
				"-format",
				"png",
				tmppath+File.separator+"image*.png"
		};
		
		try {
			byCommonsExec(cmdLine_resize);
			System.out.println("mogry complete");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void byCommonsExec(String[] command)throws IOException,InterruptedException {
	    DefaultExecutor executor = new DefaultExecutor();
	    CommandLine cmdLine = CommandLine.parse(command[0]);
	    for (int i=1, n=command.length ; i<n ; i++ ) {
	        cmdLine.addArgument(command[i]);
	    }
	    executor.execute(cmdLine);
	}

	public boolean deleteDir(String saveTmp) {
		File f = new File(saveTmp);
		if(!f.exists()){
			return false;
		}
		
		File[] files = f.listFiles();
		for( File tmp: files){
			tmp.delete();
		}
		return true;
	}

	
	
}
