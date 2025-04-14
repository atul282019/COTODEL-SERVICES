package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.VoucherTypeMasterRequest;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;


public interface VoucherTypeMasterService {
	
	//public List<VoucherTypeMasterEntity>  getVoucherTypeMaster();
	//public List<VoucherTypeDto>  getVoucherTypeList();
	public VoucherTypeMasterEntity  getVoucherTypeMasterDetail(String voucherCode);
	public VoucherTypeMasterRequest saveVoucherTypeMaster(VoucherTypeMasterRequest voucherTypeMasterRequest);
	public VoucherTypeMasterRequest updateVoucherTypeMaster(VoucherTypeMasterRequest voucherTypeMasterRequest);
}
