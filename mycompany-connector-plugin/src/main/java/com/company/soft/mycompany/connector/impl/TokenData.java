package com.company.soft.mycompany.connector.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public @Data class TokenData {
	private String access_token;
	private String token_type;
	
}
