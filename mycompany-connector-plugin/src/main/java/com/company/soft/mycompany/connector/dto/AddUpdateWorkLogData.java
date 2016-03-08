package com.company.soft.mycompany.connector.dto;

import com.company.soft.mycompany.connector.api.WorkLogData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public @Data class AddUpdateWorkLogData implements WorkLogData {
	private String login;
	private String taskId;
	private String workLogId;
	private String taskDescription;
	private String workDescription;
	private long loggedTime; // Work time in seconds
	private long loggedDate; // log date in milliseconds
}
