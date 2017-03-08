package com.pikitori.mecavo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.pikitori.util.FileUtils;
import com.pikitori.util.Property;
import com.pikitori.web.repository.PictureDao;
import com.pikitori.web.repository.PostDao;
import com.pikitori.web.vo.PictureVo;

@Repository
public class FileUploadService {
	
	@Autowired
	private FileUtils utils;
	
	@Autowired
	private PictureDao pictureDao;
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private Property property;
	
	public String updateProfileImg(MultipartFile mpf) throws IOException {
		String orgFileName = mpf.getOriginalFilename(); 
		String fileExtName = orgFileName.substring( orgFileName.lastIndexOf('.') + 1, orgFileName.length() );
		String saveFileName = utils.generateSaveFileNameNoExt( fileExtName );
		saveFileName = utils.createImageDir(property.SAVE_PIKI_IMAGE,saveFileName);
		if(property.isdev){
			return property.domain + property.image+ "/" + utils.writeThumbFile(mpf, property.SAVE_PIKI_IMAGE,saveFileName);
		}else{
			return property.server_domain + property.image+ "/" + utils.writeThumbFile(mpf, property.SAVE_PIKI_IMAGE,saveFileName);	
		}
	}
	
	private String tmpfile;
	private String tmpfilename = "image";
	
	public boolean makeMovie(List<MultipartFile> fileList, List<Long> pictureNoList,List<String> pictureType, Long postNo, Long userNo, double speed) throws IOException{
		List<PictureVo> pictureList = new ArrayList<PictureVo>();
		
		//1. 사진을 upload 합니다.
		pictureList = uploadFiles(fileList, pictureNoList,pictureType);
		
		//2. tmp 사진을 저장합니다.
		utils.deleteDir(property.SAVE_TMP);
		int i=0;
		String[] imageList = new String[fileList.size()];
		for(MultipartFile f: fileList){
			tmpfile = tmpfilename + i+ "." + "png";
			imageList[i++] = property.SAVE_TMP +"/"+ utils.writeFile(f,property.SAVE_TMP,tmpfile);
			tmpfile = "";
		}

		//3. 동영상 정보를 update 합니다.
		boolean result = false;
		String file_movie = utils.convertMp4(imageList,property.SAVE_TMP,property.SAVE_MOVIE,speed);
		System.out.println("make file is ? " + file_movie);
		File f = new File(property.SAVE_MOVIE + "/" + file_movie);
		System.out.println("make file is ?" + f.isFile() + ":" + f.getAbsolutePath());
		if(f.isFile()){
			if(property.isdev){
				result = postDao.updatePostMovie(postNo,userNo, property.domain + property.movie+ "/" + file_movie, true);
			}else{
				result = postDao.updatePostMovie(postNo,userNo, property.server_domain + property.movie+ "/" + file_movie, true);	
			}
		}else{
			//DB에서 post을 삭제합니다.
			if(property.isdev){
				postDao.updatePostMovie(postNo, userNo,property.domain + property.movie+ "/" + file_movie, false);
			}else{
				postDao.updatePostMovie(postNo,userNo, property.server_domain + property.movie+ "/" + file_movie, false);
			}
		}
		System.out.println("makeMovie result:  "+ result);
		//5. tmp 폴더를 삭제합니다.
//		return result && utils.deleteDir(SAVE_TMP);
		return result;
	}
	
	public List<PictureVo> uploadFiles(List<MultipartFile> fileList, List<Long> pictureNoList, List<String> pictureType) throws IOException{
		List<PictureVo> pictureList = new ArrayList<PictureVo>();
		PictureVo[] pictureVo = new PictureVo[fileList.size()];
		
		for(int i = 0; i < fileList.size(); i++){
			if(pictureType.get(i).startsWith("file")){
				pictureVo[i] = new PictureVo();
				pictureVo[i].setPicture_no(pictureNoList.get(i));
				//1.  file 인경우만 이미지를 저장합니다.
				String saveFileName = uploadFile(property.SAVE_PIKI_IMAGE,fileList.get(i).getOriginalFilename(), fileList.get(i));
				if(property.isdev){
					pictureVo[i].setPicture_url(property.domain + property.image + "/"+saveFileName);
					String saveThumbFileName = saveFileName.substring(0, saveFileName.lastIndexOf('.'))+"_thumb.png";
					pictureVo[i].setPicture_url_thumb(property.domain + property.image + "/"+saveThumbFileName);
				}else{
					pictureVo[i].setPicture_url(property.server_domain + property.image + "/"+saveFileName);
					String saveThumbFileName = saveFileName.substring(0, saveFileName.lastIndexOf('.'))+"_thumb.png";
					pictureVo[i].setPicture_url_thumb(property.server_domain + property.image + "/"+saveThumbFileName);
				}
				pictureVo[i].setPicture_ext("png");
				
				System.out.println("uploadFiles: " + pictureVo[i]);
				pictureList.add(pictureVo[i]);
			}
		}
		//2. file 인경우 사진 DB정보를 업데이트 합니다.
		pictureDao.updatePictureURL(pictureList);
		return pictureList;
	}
	
//	public String uploadFile(String path, String orgFileName, MultipartFile mpf) throws IOException {
//		
////		String fileExtName = orgFileName.substring( orgFileName.lastIndexOf('.') + 1, orgFileName.length() );
//		String fileExtName = "png";
//		String saveFileName = utils.generateSaveFileName( fileExtName );
//		saveFileName = utils.createImageDir(path, saveFileName);
//		System.out.println("create upload file"+ saveFileName);
//		return utils.writeFile(mpf, path, saveFileName);
//	}
	
	public String uploadFile(String path, String orgFileName, MultipartFile mpf) throws IOException {
		String fileExtName = "png";
		String saveFileName = utils.generateSaveFileNameNoExt( fileExtName );
		saveFileName = utils.createImageDir(path,saveFileName);
		System.out.println("create upload file"+ saveFileName);
		if(property.isdev){
			return utils.writeThumbFile(mpf, property.SAVE_PIKI_IMAGE,saveFileName);
		}else{
			return utils.writeThumbFile(mpf, property.SAVE_PIKI_IMAGE,saveFileName);	
		}
	}

	
}
