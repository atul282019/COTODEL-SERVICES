package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserUpdateRequest {
	private DataUpdateRequest data;
	private String event_time;
	private String type;
	private String response;
}