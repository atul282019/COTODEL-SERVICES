package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	private String cf_payment_id;	
	private String payment_status;
	private Float payment_amount;
	private String payment_currency;
	private String payment_message;
	private String payment_time;
	private String bank_reference;
	private String payment_group;
    
//     "payment_method": {
//       "app": {}
//     },
}
