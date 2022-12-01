package springAnnotation.ex01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("mainController")
//bean id "mainController"으로 자동 생성
@RequestMapping("/test")
//mapping 이름 설정
public class MainController {
	
	@RequestMapping(value="/main1.do", method = RequestMethod.GET)
	//  ~~~/test/main1.do 
	public ModelAndView mainMethod1(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("msg", "대한민국");
		mav.setViewName("main");
		return mav;
	}
	
	@RequestMapping(value="/main2.do", method = RequestMethod.GET)
	public ModelAndView mainMethod2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("msg", "랄랄라라라라라");
		mav.setViewName("main");
		return mav;
	}
}
