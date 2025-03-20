package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMeta {
	private String return_url;
	private String notify_url;
	private String payment_methods;
}

