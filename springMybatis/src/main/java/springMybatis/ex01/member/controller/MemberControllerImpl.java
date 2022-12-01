package springMybatis.ex01.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import springMybatis.ex01.member.service.MemberService;
import springMybatis.ex01.member.vo.MemberVO;

public class MemberControllerImpl extends MultiActionController implements MemberController {
	
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
	
	@Override
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO = new MemberVO();
		bind(request, memberVO); 
		/*
		 * MultiActionController���� �������ִ� �޼ҵ�
		 * request���� ���� �Ķ���Ͱ��� memberVO�� �־��ش�
		 * �Ķ���͸�� memberVO�� �ʵ���� ���� �͸�
		*/
		int result =  memberService.addMember(memberVO);
		ModelAndView mav=new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	public ModelAndView removeMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String id=request.getParameter("id");
		memberService.removeMember(id);
		ModelAndView mav=new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@RequestMapping(value = "/member/modMember.do", method = RequestMethod.GET)
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override
	public ModelAndView updateMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO = new MemberVO();
		bind(request, memberVO); 

		memberService.updateMember(memberVO);
		ModelAndView mav=new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	public ModelAndView modMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String viewName=getViewName(request);
		String id=request.getParameter("id");
		MemberVO memberVO = memberService.findMember(id);
		
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("member", memberVO);
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
			end=uri.indexOf(";"); //��û uri�� ;�� ������ ��ġ ����
		} else if(uri.indexOf("?") != -1) {
			end=uri.indexOf("?");
		} else {
			end=uri.length();
		}
		
		String fileName=uri.substring(begin, end);
		if(fileName.lastIndexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf(".")); //localhost:8090/~~~/aa.do��� .do������ ���ϸ��� ����
		}
		if(fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length()); // localhost:8090/~~~/aa���� aa�� �����´�
			//��û�� ȹ��
		}
		
		return fileName;
		
	}
}
