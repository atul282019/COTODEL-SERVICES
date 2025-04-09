package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargesDetail {
	private String service_charge;
	private String service_tax;
	private String settlement_amount;
	private String settlement_currency;
	private String service_charge_discount;
}
