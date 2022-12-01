package com.leeyerim.springBoard.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FileDownloadController extends HttpServlet {
	private static String ART_IMAGE_REPO="C:\\myJSP\\board\\article_images";

	@RequestMapping("/download.do")
	protected void download(@RequestParam("imageFileName") String imageFileName, 
			@RequestParam("articleNo") String articleNo, 
			HttpServletResponse response) throws Exception {
	
		OutputStream out=response.getOutputStream();
		String path = ART_IMAGE_REPO + "\\" + articleNo + "\\" + imageFileName;
		File imageFile = new File(path);
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment;fileName=" + imageFileName);
		FileInputStream fis=new FileInputStream(imageFile);
		byte[] buffer=new byte[1024*8];

		while(true) {
			int count = fis.read(buffer);
			if(count == -1) break;
			out.write(buffer, 0, count);
		}
		
		fis.close();
		out.close();
		
	}
}
