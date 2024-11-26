package com.cotodel.hrms.auth.server.dto.bulk;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherBulkVoucherCreateRequest {
	
	List<ErupiBulkIdRequest> data;	
	
}
