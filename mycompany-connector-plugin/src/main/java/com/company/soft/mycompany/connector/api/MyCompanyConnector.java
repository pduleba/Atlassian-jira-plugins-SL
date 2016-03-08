package com.company.soft.mycompany.connector.api;

import org.springframework.http.MediaType;

import com.company.soft.mycompany.connector.dto.AddUpdateWorkLogData;
import com.company.soft.mycompany.connector.dto.DeleteWorkLogData;
import com.company.soft.mycompany.connector.dto.WorkLogResponse;

public interface MyCompanyConnector {
	
	public MediaType APPLICATION_JSON_UTF8 = MediaType.parseMediaType("application/json; charset=utf-8");
	
	WorkLogResponse sendAddWorkLogData(AddUpdateWorkLogData data);
	WorkLogResponse sendUpdateWorkLogData(AddUpdateWorkLogData data);
	WorkLogResponse sendDeleteWorkLogData(DeleteWorkLogData data);
}