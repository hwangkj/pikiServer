package facebookSite1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

public class filecopy {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String tmppath ="C:\\Users\\admin\\Desktop\\real";
		mogry(tmppath);
		String[] imageList = new String[]{
				"C:\\Users\\admin\\Desktop\\real\\image1.png",
				"C:\\Users\\admin\\Desktop\\real\\image2.png",
				"C:\\Users\\admin\\Desktop\\real\\image3.png",
				"C:\\Users\\admin\\Desktop\\real\\image4.png",
				"C:\\Users\\admin\\Desktop\\real\\image5.png",
				"C:\\Users\\admin\\Desktop\\real\\image6.png",
				"C:\\Users\\admin\\Desktop\\real\\image7.png",
				"C:\\Users\\admin\\Desktop\\real\\image8.png",
				"C:\\Users\\admin\\Desktop\\real\\image9.png",
				"C:\\Users\\admin\\Desktop\\real\\image10.png",
				"C:\\Users\\admin\\Desktop\\real\\image11.png",
				};
		int k = 0;
		int value = 25;
		try {
		for( int t= 0; t < imageList.length ; t++){
			File file = new File(imageList[t]);
			BufferedImage i = ImageIO.read(file); // 변형하고자 하는 이미지
			for( k = t*value; k <( value * (t+1)); k++){
				File tmp = new File("C:\\Users\\admin\\Desktop\\real\\img"+k+".png");
				tmp.createNewFile();
				ImageIO.write(i, "png", tmp); // 변형하고자 하는 이미지
			}
		}

		} catch (Exception e) {
			System.out.println(e);
		}
		ffmpeg(tmppath);
		long end = System.currentTimeMillis();
		long gap = end - start;
		System.out.println(" complete time: "+ gap/1000 + "초");
	}
	private static final String ffmpegPath = "/Users/admin/Downloads/source/ffmpeg-win64/ffmpeg/bin/ffmpeg.exe";
	
	public static void mogry(String tmppath){

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
				tmppath+"\\"+"image*.png"
		};
		
		try {
			byCommonsExec(cmdLine_resize);
			System.out.println("mogry complete");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void ffmpeg(String tmppath){
		String fOriginal = tmppath +"\\img%00d.png"; // 실시간으로 업로드되는 파일
		String fResult = tmppath+"\\"+"result.mp4";
		
//		String[] cmdLine = new String[] {
//				ffmpegPath, 
//				"-i ", fOriginal,
//				"-c:v ", "libx264",
//				"-preset ", "slow",
//				"-crf ", "0",
//				"-maxrate ", "600k",
//				"-bufsize ", "1830k",
//				fResult ,"-y"};
		
		String[] music = new String[]{
				"C:\\piki_movie\\music\\1.mp3",
				"C:\\piki_movie\\music\\2.mp3",
				"C:\\piki_movie\\music\\3.mp3",
				"C:\\piki_movie\\music\\4.mp3",
				"C:\\piki_movie\\music\\5.mp3",
				"C:\\piki_movie\\music\\6.mp3",
				"C:\\piki_movie\\music\\7.mp3"};
		Random r = new Random();
		int track = r.nextInt(music.length);
		
		double tempo = 1.0;
		int frame = 11;
		System.out.println("track:" + track + "tempo: " + tempo);
//		String fOriginal = tmppath+"\\img%000d.png"; // 실시간으로 업로드되는 파일
//		String filename = generateSaveFileName("mp4");
//		String fResult = resultpath+"/"+filename;
		
		String[] cmdLine = new String[] {
				ffmpegPath,
				"-f", "image2",
				"-s", "xga",
				"-i ", fOriginal,
				"-ss", "00:00:30",
				"-t", String.valueOf(frame),
				"-i ", music[track],
				"-af ","atempo="+String.valueOf(tempo),
				"-q:v","0",
				"-vcodec ", "libx264",
				"-preset ", "slow",
				"-pix_fmt", "yuv420p",
				"-level ", "3.0",
				fResult ,"-y"};	
		
//		String[] cmdLine = new String[] {
//				ffmpegPath,
//				"-f", "image2",
//				"-s", "1024x768",
//				"-i ", fOriginal,
//				"-q:v","0",
//				"-vcodec ", "libx264",
//				"-preset ", "slow",
//				"-pix_fmt", "yuv420p",
//				"-level ", "3.0",
//				fResult ,"-y"};	
		
		
		try {
			byCommonsExec(cmdLine);
			System.out.println("ffmpeg complete");
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void byCommonsExec(String[] command)throws IOException,InterruptedException {
	    DefaultExecutor executor = new DefaultExecutor();
	    CommandLine cmdLine = CommandLine.parse(command[0]);
	    for (int i=1, n=command.length ; i<n ; i++ ) {
	        cmdLine.addArgument(command[i]);
	    }
	    executor.execute(cmdLine);
	}
	
	//200*5 = 40 fps 25 -> 200/25 = 8초 *5 = 40초
	//1초 25* 

	public static void fileCopy(String input, String output) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		int i;
		int count = 0;
		int total = 0;
		try {
			fis = new FileInputStream("C:\\Users\\admin\\Desktop\\img\\image0.png");
			fos = new FileOutputStream(output);

			byte buffer[] = new byte[1024];

			while ((i = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, i);
				fos.flush();
				count++;
				total += i;
				System.out.println("count = " + count);
			}
			System.out.println("====파일 복사 완료====");
			System.out.println(total + "복사 ");
			System.out.println(output + "을 생성");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
