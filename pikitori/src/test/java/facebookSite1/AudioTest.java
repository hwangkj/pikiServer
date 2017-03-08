package facebookSite1;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.net.ContentHandler;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AudioTest {
	public static void main(String[] args) {
		
		AudioTest mp3 = new AudioTest();
//		mp3.getMP34();
		System.out.println(mp3.makepoint("C:\\piki_movie\\music\\7.mp3"));
	}
	
	public void getMP3() throws UnsupportedAudioFileException, IOException{
		File f = new File("C://piki/2.mp3");
//		AudioInputStream in = AudioSystem.getAudioInputStream(f);
//		AudioFormat base = in.getFormat();
		AudioFileFormat aff = AudioSystem.getAudioFileFormat(f);
	    if (aff instanceof AudioFileFormat) {
	        Map<?, ?> properties = ((AudioFileFormat)aff).properties();
	        String key = "duration";
	        Long microseconds = (Long) properties.get(key);
	        int mili = (int) (microseconds / 1000);
	        int sec = (mili / 1000) % 60;
	        int min = (mili / 1000) / 60;
	        System.out.println("time = " + min + ":" + sec);
	    } else {
	        throw new UnsupportedAudioFileException();
	    }
	   
	    
//		AudioFormat af = aff.getFormat();
//		
//		System.out.println("play time:" + aff.getFrameLength() / af.getFrameRate() );
		
		
	}

	public void getMP33(){
		String file  = "C:\\piki\\a.txt";
		String file1 = "‪C:\\piki\\a.png";
		File f = new File(file);
		File f1 = new File(file1);
		BufferedWriter writer = null;
		System.out.println(f.isFile());
		if(!f.isFile()){
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
				writer.write("test from yeon");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			BufferedImage i = ImageIO.read(f1);
			System.out.println();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
//		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fildLocation));
//		AudioFormat format = audioInputStream.getFormat();
//		long frames = audioInputStream.getFrameLength();
//		double durationInseconds = (frames+0.0)/format.getFrameRate();
//		System.out.println(durationInseconds);
		
//		InputStream input = new FileInputStream(new File(fildLocation));
//		ContentHandler handler = new DefaultHandler();
//		Metadata  metadata = new Metadata ();
//		
		
	}

	public void getMP34(){
        String fileLocation = "C://piki/3.mp3";

	        try {
	
	        InputStream input = new FileInputStream(new File(fileLocation));
	        DefaultHandler handler = new DefaultHandler();
	        Metadata metadata = new Metadata();
	        Parser parser = new Mp3Parser();
	        ParseContext parseCtx = new ParseContext();
	        parser.parse(input, handler, metadata, parseCtx);
	        input.close();
	
	        // List all metadata
	        String[] metadataNames = metadata.names();
	
	        for(String name : metadataNames){
	        System.out.println(name + ": " + metadata.get(name));
	        }
	
	        // Retrieve the necessary info from metadata
	        // Names - title, xmpDM:artist etc. - mentioned below may differ based
	        System.out.println("----------------------------------------------");
	        System.out.println("Title: " + metadata.get("title"));
	        System.out.println("Artists: " + metadata.get("xmpDM:artist"));
	        System.out.println("Composer : "+metadata.get("xmpDM:composer"));
	        System.out.println("Genre : "+metadata.get("xmpDM:genre"));
	        System.out.println("Album : "+metadata.get("xmpDM:album"));
	        String s = metadata.get("xmpDM:duration");
	        System.out.println("duration : "+ s);
	        Float milis = Float.parseFloat(s);
	        int abs = (int) Math.abs(milis);
	        System.out.println(abs);
	        String duration = String.format("%02d:%02d:%02d",0 ,abs/60000 ,(abs%60000)/1000 );
	        System.out.println("duration : "+ duration);
	        } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        } catch (IOException e) {
	        e.printStackTrace();
	        } catch (SAXException e) {
	        e.printStackTrace();
	        } catch (TikaException e) {
	        e.printStackTrace();
	        }
    }

	private String makepoint(String fileLocation) {
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
		        String point_duration = String.format("%02d:%02d:%02d", 0 ,point/60000 ,(point%60000)/1000 );
		        System.out.println("duration : "+ duration);
		        System.out.println("duration : "+ point_duration);
		        return point_duration;
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
}