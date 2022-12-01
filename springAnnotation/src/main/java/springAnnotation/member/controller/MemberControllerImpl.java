package springAnnotation.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import springAnnotation.member.service.MemberService;
import springAnnotation.member.vo.MemberVO;

//action-servlet.xml에 서블릿 맵핑하는 부분이 없고 그 역할을 annotation이 대신한다
@Controller("memberController")
public class MemberControllerImpl extends MultiActionController implements MemberController {
	
	//memberService 주입, setter 필요 X
	/*
	 * <bean id="memberController" class="springMybatis.ex01.member.controller.MemberControllerImpl">
			<property name="methodNameResolver">
				<ref local="methodResolver"/>
			</property>
			<property name="memberService" ref="memberService"/>
		</bean>
	 * */
	@Autowired
	private MemberService memberService;
	
	//new MemberVO() : memberVO 생성 필요 X
	//MemberVO.java의 @Component("memberVO")로 생성한 객체 주입 
	@Autowired
	private MemberVO memberVO;

	//@RequestMapping(value = "mapping 정보", method= get, post 받을 방법)
	@Override
	@RequestMapping(value = "/member/listMembers.do", 
		method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		List memberList = memberService.listMembers();
		ModelAndView mav=new ModelAndView(viewName);
		mav.addObject("memberList", memberList);
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//@ModelAttribute("memberVO") MemberVO memberVO
		// ->  
		
		request.setCharacterEncoding("utf-8");
		//MemberVO memberVO = new MemberVO(); -> 삭제, 어노테이션으로 주입된 객체 사용
		// bind(request, memberVO); -> @ModelAttribute 가 바인딩 해준다
		/*
		 * MultiActionController에서 제공해주는 메소드
		 * request에서 받은 파라미터값을 memberVO에 넣어준다
		 * 파라미터명과 memberVO의 필드명이 같은 것만
		*/
		int result =  memberService.addMember(memberVO);
		ModelAndView mav=new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//String id=request.getParameter("id"); 
		// -> 어노테이션으로 받아온다 : @RequestParam("id") String id
		memberService.removeMember(id);
		ModelAndView mav=new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/modMember.do", method = RequestMethod.GET)
	public ModelAndView modMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String viewName=getViewName(request);
		//String id=request.getParameter("id");
		MemberVO memberVO = memberService.findMember(id);
		
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("member", memberVO);
		return mav;
	}
	
	@RequestMapping(value = "/member/memberForm.do", method = RequestMethod.GET)
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/updateMember.do",
		method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView updateMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//MemberVO memberVO = new MemberVO();
		//bind(request, memberVO); 

		memberService.updateMember(memberVO);
		ModelAndView mav=new ModelAndView("redirect:/member/listMembers.do");
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
			fileName = fileName.substring(0, fileName.lastIndexOf(".")); //localhost:8090/~~~/aa.do라면 .do제외한 파일명이 들어간다
		}
		if(fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length()); // localhost:8090/~~~/aa에서 aa만 가져온다
			//요청명 획득
		}
		
		return fileName;
		
	}
}
