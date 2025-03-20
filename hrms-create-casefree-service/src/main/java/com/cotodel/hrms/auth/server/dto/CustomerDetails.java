package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {
	private String customer_id;
	private String customer_name;
	private String customer_email;
	private String customer_phone;
	private String customer_uid;
}
