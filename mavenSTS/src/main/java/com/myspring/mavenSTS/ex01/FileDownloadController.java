package com.myspring.mavenSTS.ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

@Controller
public class FileDownloadController {
	private static String IMAGE_REPO_PATH="images 폴더 경로";
	
	/*@RequestMapping("/download")
	public void download(@RequestParam("imageFileName") String imageFileName,
			HttpServletRequest response) throws Exception {
		OutputStream out = response.getOutputStream(); //저장할 때 사용
		String downFile=IMAGE_REPO_PATH + "\\" + imageFileName;
		File file = new File(downFile);
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment;fileName=" + imageFileName);
		
		FileInputStream in=new FileInputStream(file);
		byte[] buffer=new byte[1024*8];
		while(true) {
			int count = in.read(buffer);
			if(count == -1) {
				break;
			}
			out.write(buffer, count);
		}
		in.close();
		out.close();
	}*/
	/*썸네일 라이브러리 활용*/
	@RequestMapping("/download")
	public void download(@RequestParam("imageFileName") String imageFileName,
			HttpServletRequest response) throws Exception {
		OutputStream out = response.getOutputStream(); //저장할 때 사용
		String downFile=IMAGE_REPO_PATH + "\\" + imageFileName;
		File file = new File(downFile);
		int lastIndex=imageFileName.lastIndexOf(".");
		String fileName=imageFileName.substring(0, lastIndex);
		File thumbnail=new File(IMAGE_REPO_PATH + "\\thumbnail\\" + fileName + ".png");
		if(file.exists()) {
			thumbnail.getParentFile().mkdirs();
			Thumbnails.of(file).size(50, 50).outputFormat("png").toFile(thumbnail);
		}
		
		FileInputStream in=new FileInputStream(thumbnail);
		byte[] buffer=new byte[1024*8];
		while(true) {
			int count = in.read(buffer);
			if(count == -1) {
				break;
			}
			out.write(buffer, count);
		}
		in.close();
		out.close();
	}
}
