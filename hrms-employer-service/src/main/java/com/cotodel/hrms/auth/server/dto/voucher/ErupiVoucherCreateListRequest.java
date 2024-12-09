package com.cotodel.hrms.auth.server.dto.voucher;
import java.util.List;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateListRequest {
	
	List<ErupiVoucherCreateDetailsRequest> data;

}
