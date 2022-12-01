package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.spring.member.service.MemberService;
import com.spring.member.vo.MemberVO;

public class MemberControllerImpl extends MultiActionController implements MemberController{
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		List memberList = memberService.listMembers();
		ModelAndView mav=new ModelAndView(viewName);
		mav.addObject("memberList", memberList);
		return mav;
	}
	
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO=new MemberVO();
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		
		memberVO.setId(id);
		memberVO.setPwd(pwd);
		memberVO.setName(name);
		memberVO.setEmail(email);
		int result=memberService.addMember(memberVO);
		
		List memberList = memberService.listMembers();
		ModelAndView mav=new ModelAndView("listMembers");
		//그냥 jsp만 받으면 목록을 불러오지 않기 때문에 목록을 받아와서 ModelAndView로 넘겨준다
		mav.addObject("memberList", memberList);
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
		//localhost:8090/~~~/aa.do로 가져온다 속성=값 과같은 부분은 제외한 부분

		if(fileName.lastIndexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf(".")); //localhost:8090/~~~/aa.do라면 .do제외한 파일명이 들어간다
		}
		if(fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length()); // localhost:8090/~~~/aa에서 aa만 가져온다
			//요청명 획득
		}
		
		
		return fileName;
		
	}
}
