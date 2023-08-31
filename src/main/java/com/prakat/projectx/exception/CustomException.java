package com.prakat.projectx.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{

	private static final long serialVersionUID = -5482630249807649942L;
	private String errorMsg;
	private int errorCode;
	private Optional<String> debugMessage;

	public CustomException(String errorMsg,int errorCode){
	super();
	this.errorCode=errorCode;
	this.errorMsg=errorMsg;
}
}
