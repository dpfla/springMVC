package springMVC.ex02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class UserController extends MultiActionController {
	
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id="";
		String pwd="";
		String viewName=getViewName(request);
		//요청명과 같은 jsp 만들어서 사용
		ModelAndView mav=new ModelAndView();
		//멀티 컨트롤러는 객체 생성하여 작성
		request.setCharacterEncoding("utf-8");
		id=request.getParameter("id");
		pwd=request.getParameter("pwd");
		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		mav.setViewName(viewName); //포워딩할 JSP이름 설정
		return mav;
	}
	
	public ModelAndView memberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav=new ModelAndView();
		String viewName=getViewName(request);
		request.setCharacterEncoding("utf-8");
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		
		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		mav.addObject("name", name);
		mav.addObject("email", email);
		mav.setViewName(viewName);
		return mav;
	}
	
	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath=request.getContextPath();
		String uri=(String)request.getAttribute("javax.servlet.include.request_uri");
		
		if (uri == null || uri.trim().equals("")) {
			uri=request.getRequestURI();
		}
		int begin=0, end;
		if(!(contextPath == null) || "".equals(contextPath)) {
			begin=contextPath.length();
		}
		if(uri.indexOf(";") != -1) {
			end=uri.indexOf(";"); //요청 uri에 ;가 있으면 위치 구함
		} else if(uri.indexOf("?") != -1) {
			end=uri.indexOf("?");
		} else {
			end=uri.length();
		}
		
		String fileName=uri.substring(begin, end);
		if(fileName.lastIndexOf(".") != -1) {
			fileName.substring(0, fileName.lastIndexOf(".")); //localhost:8090/~~~/aa.do라면 .do제외한 파일명이 들어간다
		}
		if(fileName.lastIndexOf("/") != -1) {
			fileName.substring(fileName.lastIndexOf("/"), fileName.length()); // localhost:8090/~~~/aa에서 aa만 가져온다
			//요청명 획득
		}
		
		return fileName;
		
	}
		
	
}
