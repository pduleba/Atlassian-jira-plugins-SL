package com.company.soft.mycompany.connector.dto;

import com.company.soft.mycompany.connector.api.WorkLogData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public @Data class DeleteWorkLogData implements WorkLogData {
	private String login;
	private String taskId;
}
