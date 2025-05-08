package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.ExpenseBillReader;
import com.cotodel.hrms.auth.server.dto.ExpenseBillReaderRequest;

public interface OcrReaderService {
	
	public ExpenseBillReader  ocrDetail(ExpenseBillReaderRequest expenseBillReaderRequest);
}
