package springAnnotation.ex02;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("loginController")
public class LoginController {

	@RequestMapping(value = {"/test/loginForm.do", "/test/loginForm2.do"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//한 메소드에 두개의 mapping 이름을 넣을 수 있다
		//전송 방법도 여러개 넣을 수 있다
		ModelAndView mav=new ModelAndView();
		mav.setViewName("loginForm");
		return mav;
	}
	/*
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		return mav;
	}*/
	
	/*id, pwd라는 매개변수가 있을 때, path에 id, pwd있으면 오류 일으킴
	 * @RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@RequestParam("id") String id, @RequestParam("pwd") String pwd, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		return mav;
	}*/
	
	/*
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@RequestParam(value="id", required = false) String id, @RequestParam(value="pwd", required = false) String pwd, 
			@RequestParam(value="email", required = false) String email, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		mav.addObject("id", id);
		mav.addObject("pwd", pwd);
		mav.addObject("email", email);
		return mav;
	}*/
	
	/*
	//값을 받아서 넘기는 것 X, 
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(Model model,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		model.addAttribute("id", "hong");
		model.addAttribute("pwd", "hong123");
		model.addAttribute("email", "hong@example.com");
		return "result";
	}*/
	
	/*//Map으로 정보 전달
	 * @RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@RequestParam Map<String, String> memInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		mav.addObject("memInfo", memInfo);
		return mav;
	}*/
	
	/*
	//VO 클래스 활용
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@ModelAttribute("memInfo") LoginVO loginVO,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav=new ModelAndView();
		mav.setViewName("result");
		return mav;
	}*/
	
	
	
	
}
