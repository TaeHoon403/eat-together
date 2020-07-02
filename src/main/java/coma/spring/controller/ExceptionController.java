package coma.spring.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler
	public String exceptionHandler(Exception e) {
		e.printStackTrace();
		System.out.println("Exception Handler : 에러가 발생하였습니다.");
		return "error";
	}

	@ExceptionHandler
	public String exceptionHandler(NumberFormatException nfe) {
		nfe.printStackTrace();
		System.out.println("NumberFormatException Handler : 에러가 발생하였습니다.");
		return "error";
	}

	@ExceptionHandler
	public String exceptionHandler(NullPointerException npe) {
		npe.printStackTrace();
		System.out.println("NullPointerException Handler : 에러가 발생하였습니다.");
		return "error";
	}
}
