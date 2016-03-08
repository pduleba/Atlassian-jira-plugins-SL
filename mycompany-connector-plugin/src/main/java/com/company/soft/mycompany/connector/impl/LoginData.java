package com.company.soft.mycompany.connector.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@ToString
public @Data class LoginData {
	private String grantType;
	private String username;
	private String password;
}
