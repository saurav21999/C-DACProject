package com.onlineshopping.security;

import org.springframework.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaltVal {

	@Value("${spring.saltvalue}")
	String salt;
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt)
	{
		this.salt=salt;
	}
	
}
