package com.cotodel.hrms.auth.server.dto.bulk;

import java.util.List;

import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadSuccessEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherBulkUploadSFListResponse {
	
	private String totalCount;
	private String successCount;
	private String failCount;
	List<VoucherBulkUploadSuccessEntity> success;
	List<VoucherBulkUploadFailEntity> fail;
	
}
