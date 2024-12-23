package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiMultipleVoucherCreateRequest {
	
	private List<ErupiVoucherCreateDetailsRequest> list;
	
	
}
