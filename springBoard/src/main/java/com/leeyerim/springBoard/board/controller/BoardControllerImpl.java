package com.leeyerim.springBoard.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.impl.AvalonLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import com.leeyerim.springBoard.board.service.BoardService;
import com.leeyerim.springBoard.board.vo.ArticleVO;
import com.leeyerim.springBoard.board.vo.ImageVO;
import com.leeyerim.springBoard.member.vo.MemberVO;

@Controller("boardController")
public class BoardControllerImpl implements BoardController  {
	private static String ART_IMAGE_REPO="C:\\myJSP\\board\\article_images";
	
	@Autowired
	BoardService boardService;
	@Autowired
	ArticleVO articleVO;
	
	@Override
	@RequestMapping(value = "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		List<ArticleVO> articleList = boardService.listArticles();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("articleList", articleList);
		return mav;
	}
	
	/*//한개 이미지 추가하기
	@Override
	@RequestMapping(value = "/board/addNewArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		String imageFileName=upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO=(MemberVO)session.getAttribute("member");
		String id=memberVO.getId();
		articleMap.put("parentNo", 0);
		articleMap.put("id", id);
		articleMap.put("imageFileName", imageFileName);
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders=new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try {
			int articleNo=boardService.addNewArticle(articleMap);
			
			if(imageFileName != null && imageFileName.length() != 0) {
				File srcFile = new File(ART_IMAGE_REPO + "\\temp\\" + imageFileName);
				File destDir = new File(ART_IMAGE_REPO + "\\" + articleNo);
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
			}	
			
			message = "<script>";
			message += "alert('새 글을 추가했습니다');";
			message += "location.href='" + multipartRequest.getContextPath()+"/board/listArticles.do'";
			message += "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			
		} catch (Exception e) {
			File srcFile = new File(ART_IMAGE_REPO + "\\temp\\" + imageFileName);
			srcFile.delete();
			message = "<script>";
			message += "alert('오류가 발생했습니다. 다시 시도해주세요');";
			message += "location.href='" + multipartRequest.getContextPath()+"/board/articleForm.do";
			message += "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
			System.out.println("글 쓰기 제어 처리중 오류: " + e.getMessage());
		}
		
		return resEnt;
	}*/
	
	//다중 이미지 추가
	@Override
	@RequestMapping(value = "/board/addNewArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO=(MemberVO)session.getAttribute("member");
		String id=memberVO.getId();
		articleMap.put("parentNo", 0);
		articleMap.put("id", id);
		
		String imageFileName=null;
		List<String> fileList=upload(multipartRequest);
		List<ImageVO> imageFileList=new ArrayList<ImageVO>();
		if(fileList != null && fileList.size() != 0) {
			for(String fileName: fileList) {
				ImageVO imageVO = new ImageVO();
				imageVO.setImageFileName(fileName);
				imageFileList.add(imageVO);
			}
			articleMap.put("imageFileList", imageFileList);
		}
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders=new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try {
			int articleNo=boardService.addNewArticle(articleMap);
			
			if(imageFileList != null && imageFileList.size() != 0) {
				for(ImageVO imageVO: imageFileList) {
					imageFileName = imageVO.getImageFileName();
					File srcFile = new File(ART_IMAGE_REPO + "\\temp\\" + imageFileName);
					File destDir = new File(ART_IMAGE_REPO + "\\" + articleNo);
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
			}	
			
			message = "<script>";
			message += "alert('새 글을 추가했습니다');";
			message += "location.href='" + multipartRequest.getContextPath()+"/board/listArticles.do'";
			message += "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			
		} catch (Exception e) {
			if(imageFileList != null && imageFileList.size() != 0) {
				for(ImageVO imageVO: imageFileList) {
					imageFileName = imageVO.getImageFileName();
					File srcFile = new File(ART_IMAGE_REPO + "\\temp\\" + imageFileName);
					srcFile.delete();
				}
			}
			
			message = "<script>";
			message += "alert('오류가 발생했습니다. 다시 시도해주세요');";
			message += "location.href='" + multipartRequest.getContextPath()+"/board/articleForm.do";
			message += "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
			System.out.println("글 쓰기 제어 처리중 오류: " + e.getMessage());
		}
		
		return resEnt;
	}
	
	
	@RequestMapping(value="/board/articleForm.do", method=RequestMethod.GET)
	private ModelAndView articleForm(HttpServletRequest request, HttpServletResponse response) {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	/*//한 개 이이미 보여주기
	@Override
	@RequestMapping(value = "/board/viewArticle.do", method=RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNo") int articleNo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		articleVO = boardService.viewArticle(articleNo);
		ModelAndView mav=new ModelAndView();
		mav.addObject("article", articleVO);
		mav.setViewName(viewName);
		return mav;
	}*/

	@Override
	@RequestMapping(value = "/board/viewArticle.do", method=RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNo") int articleNo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		Map articleMap = boardService.viewArticle(articleNo);
		ModelAndView mav=new ModelAndView();
		mav.addObject("articleMap", articleMap);
		mav.setViewName(viewName);
		return mav;
	}
	
	/*@Override
	@RequestMapping(value = "/board/modArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		String imageFileName=upload(multipartRequest);
		articleMap.put("imageFileName", imageFileName);
		String articleNo = (String)articleMap.get("articleNo");
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders=new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html;charset=utf-8");
		try {
			boardService.modArticle(articleMap);			
			if(imageFileName != null && imageFileName.length() != 0) {
				File srcFile = new File(ART_IMAGE_REPO + "\\temp\\" + imageFileName);
				File destDir = new File(ART_IMAGE_REPO + "\\" + articleNo);
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				String originalFileName=(String)articleMap.get("originalFileName");
				File oldFile=new File(ART_IMAGE_REPO + "\\" + articleNo + "\\" + originalFileName);
				oldFile.delete();
			}	
			
			message = "<script>";
			message += "alert('글을 수정했습니다');";
			message += "location.href='" + multipartRequest.getContextPath()+"/board/viewArticle.do?articleNo=" + articleNo + "';";
			message += "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			
		} catch (Exception e) {
			File srcFile = new File(ART_IMAGE_REPO + "\\temp\\" + imageFileName);
			srcFile.delete();
			message = "<script>";
			message += "alert('오류가 발생했습니다. 다시 시도해주세요');";
			message += "location.href='" + multipartRequest.getContextPath()+"/board/viewArticle.do?articleNo=" + articleNo + "';";
			message += "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
			System.out.println("글 쓰기 제어 처리중 오류: " + e.getMessage());
		}
		
		return resEnt;
	}*/

	
	
	@Override
	@RequestMapping(value = "/board/removeArticle.do", method=RequestMethod.GET)
	public ResponseEntity removeArticle(@RequestParam("articleNo") int articleNo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html; charset=utf-8");
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders=new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			boardService.removeArticle(articleNo);
			File destDir=new File(ART_IMAGE_REPO + "\\" + articleNo);
			FileUtils.deleteDirectory(destDir);
			message = "<script>";
			message += "alert('글을 삭제했습니다');";
			message += "location.href='"+request.getContextPath()+"/board/listArticles.do';";
			message += "</script>";
			resEnt=new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			message = "<script>";
			message += "alert('작업 중 오류가 발생했습니다. 다시 시도래 주세요');";
			message += "location.href='"+request.getContextPath()+"/board/listArticles.do';";
			message += "</script>";
			resEnt=new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	/*//한개 이미지 파일 업로드
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		String imageFileName=null;
		Iterator<String> fileNames=multipartRequest.getFileNames();
		while (fileNames.hasNext()) {
			String fileName=fileNames.next();
			MultipartFile multiFile=multipartRequest.getFile(fileName);
			imageFileName = multiFile.getOriginalFilename();
			File file=new File(ART_IMAGE_REPO + "\\temp\\" + fileName);
			if(multiFile.getSize() != 0) {
				if (!file.exists()) {
					file.getParentFile().mkdir();
					file.createNewFile();
				}
				multiFile.transferTo(new File(ART_IMAGE_REPO + "\\temp\\" + imageFileName));	
			}
		}
		
		return imageFileName;
	}*/
	
	//다중 이미지 파일 업로드
	private List<String> upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<String> fileList=new ArrayList<String>();
		Iterator<String> fileNames=multipartRequest.getFileNames();
		while (fileNames.hasNext()) {
			String fileName=fileNames.next();
			MultipartFile multiFile=multipartRequest.getFile(fileName);
			String originalImageFileName = multiFile.getOriginalFilename();
			fileList.add(originalImageFileName);
			File file=new File(ART_IMAGE_REPO + "\\temp\\" + fileName);
			if(multiFile.getSize() != 0) {
				if (!file.exists()) {
					file.getParentFile().mkdir();
					file.createNewFile();
				}
				multiFile.transferTo(new File(ART_IMAGE_REPO + "\\temp\\" + originalImageFileName));	
			}
		}

		return fileList;
	}

	@Override
	public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
