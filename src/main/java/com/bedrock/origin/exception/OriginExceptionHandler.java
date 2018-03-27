package com.bedrock.origin.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bedrock.origin.utils.JSONResult;

/**
 * 统一异常处理
 * <p>Title: OriginExceptionHandler</p>  
 * <p>Description: </p>  
 * @author liuxiangtao90  
 * @date 2018年3月27日 下午7:09:09
 */
@Controller
@ControllerAdvice 
public class OriginExceptionHandler {

	public static final String IMOOC_ERROR_VIEW = "info";
	
	@ExceptionHandler(value = Exception.class)
    public Object errorHandler(HttpServletRequest reqest, 
    		HttpServletResponse response, Exception e) throws Exception {
    	e.printStackTrace();
    	if (isAjax(reqest)) {
    		return "redict:ajaxError.do";
    	} else {
    		ModelAndView mav = new ModelAndView();
            mav.addObject("exception", e);
            mav.addObject("url", reqest.getRequestURL());
            mav.setViewName(IMOOC_ERROR_VIEW);
            return mav;
    	}
    }
	
	@PostMapping("ajaxError.do")
	public JSONResult ajaxErrorHandler()
	{
		return JSONResult.errorMsg("ajax异常");
	}
	
	public static boolean isAjax(HttpServletRequest httpRequest){
		return  (httpRequest.getHeader("X-Requested-With") != null  
					&& "XMLHttpRequest"
						.equals( httpRequest.getHeader("X-Requested-With").toString()) );
	}
}
