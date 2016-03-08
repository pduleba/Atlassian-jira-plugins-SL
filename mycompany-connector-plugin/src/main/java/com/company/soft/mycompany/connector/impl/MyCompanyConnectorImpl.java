package com.company.soft.mycompany.connector.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.company.soft.mycompany.connector.api.MyCompanyConnector;
import com.company.soft.mycompany.connector.api.WorkLogData;
import com.company.soft.mycompany.connector.dto.AddUpdateWorkLogData;
import com.company.soft.mycompany.connector.dto.DeleteWorkLogData;
import com.company.soft.mycompany.connector.dto.WorkLogResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ExportAsService
@Component
@Slf4j
public @Data class MyCompanyConnectorImpl implements MyCompanyConnector {
	
	private String urlWorkLog;
	private String urlToken;
	private LoginData loginData;
	private TokenData tokenData;
	
	/**
	 * Constructor need file classpath:/config.properties with urls.
	 * @throws IOException 
	 */
	public MyCompanyConnectorImpl() {
		super();
		final String filename = "config.properties";
		try( InputStream input = getClass().getClassLoader().getResourceAsStream(filename) ) {
			Properties properties = new Properties();
			properties.load(input);
			this.urlWorkLog = properties.getProperty("mycompany.connector.url.worklog");
			this.urlToken = properties.getProperty("mycompany.connector.url.token");
			this.loginData = new LoginData(
					properties.getProperty("mycompany.connector.token.grantType"),
					properties.getProperty("mycompany.connector.token.username"),
					properties.getProperty("mycompany.connector.token.password"));
		} catch( IOException e) {
			log.error(e.getMessage(), e);
		}
		this.tokenData = new TokenData("","");
	}
	
	public WorkLogResponse sendAddWorkLogData(AddUpdateWorkLogData data) {
		log.debug("Sending add: {}", data.toString());
		return sendWorklogDataWithCheck(data, HttpMethod.PUT );
	}

	public WorkLogResponse sendUpdateWorkLogData(AddUpdateWorkLogData data) {
		log.debug("Sending update: {}", data.toString());
		return sendWorklogDataWithCheck(data, HttpMethod.POST );
	}

	public WorkLogResponse sendDeleteWorkLogData(DeleteWorkLogData data) {
		log.debug("Sending delete: {}", data.toString());
		return sendWorklogDataWithCheck(data, HttpMethod.DELETE );
	}

	WorkLogResponse sendWorklogData(WorkLogData data, HttpMethod httpMethod) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(APPLICATION_JSON_UTF8));
		headers.add("Authorization", tokenData.getToken_type() + " " + tokenData.getAccess_token());
		HttpEntity<WorkLogData> entity = new HttpEntity<WorkLogData>(data, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(urlWorkLog, httpMethod, entity, String.class);
		} catch( HttpStatusCodeException ex ) {
			return WorkLogResponseFactory.create(ex);
		}
		return WorkLogResponseFactory.create(response);
	}
	
	WorkLogResponse sendWorklogData1(WorkLogData data, HttpMethod httpMethod) { //this delegation is needed for test some case
		return sendWorklogData(data, httpMethod);
	}
	
	WorkLogResponse sendWorklogDataWithCheck(WorkLogData data, HttpMethod httpMethod) {
		WorkLogResponse response = sendWorklogData1(data, httpMethod);
		if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) { //retry once if 401 occurs
			
			try {
				this.tokenData = auth();
			} catch( HttpStatusCodeException ex ) {
				log.error(ex.toString(), ex);
				return response = WorkLogResponseFactory.createInvalidAuth(ex);
			}
			
			response = sendWorklogData(data, httpMethod);
		}
		return response;
	}
	
	TokenData auth() {
		log.debug("Sending getAuthToken: {}", loginData.toString());
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(APPLICATION_JSON_UTF8));
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", loginData.getGrantType());
		map.add("username", loginData.getUsername());
		map.add("password", loginData.getPassword());
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		return restTemplate.exchange(urlToken, HttpMethod.POST, entity, TokenData.class).getBody();
	}
}