package com.myspring.mavenSTS.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {
	private static String IMAGE_REPO_PATH="images 폴더 경로";
	
	@RequestMapping(value="/form" )
	public String form() {
		return "uploadForm";//uploadForm이름의 jsp를 열어라
	}
	
	@@RequestMapping(value="/upload", method=RequestMethod.POST)
	public ModelAndView upload(MultipartHttpServletRequest multipartRequest, 
			HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		Map map=new HashMap();
		Enumeration enu=multipartRequest.getParameterNames();
		while (enu.hasMoreElements()) {
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			System.out.println(name + ": " + value);
			map.put(name, value);
		}
		List fileList= fileProcess(multipartRequest);
		map.put("fileList", fileList);
		ModelAndView mav=new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("result");
		return mav;
	}
	
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
		//업로드 기능 처리
		List<String> fileList=new ArrayList<String>();
		Iterator<String> fileNames=multipartRequest.getFileName();
		while (fileNames.hasNext()) {
			String fileName=fileNames.next();
			MultipartFile mFile=multipartRequest.getFile(fileName);
			String originalFileName=mFile.getOriginalFilename();
			fileList.add(originalFileName);
			File file = new File(IMAGE_REPO_PATH + "\\" + fileName);
			if(mFile.getSize() != 0) {
				if(!file.exists()) {
					if(file.getParentFile().mkdir()) {
						file.createNewFile();
					}
				}
				mFile.transferTo(new File(IMAGE_REPO_PATH + "\\" + originalFileName));
			}
		}
		return fileList;
	}
}
