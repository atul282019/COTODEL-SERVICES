package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceTravelRequestStatus {
	private Integer id;
	private String  sequeneId;
	private Long employerId;
	private Long employeeId;
	private String  requestType;
	private String  expenseCategoryId;
	private String  expenseCategory;
	private String  mode;
	private String  toBeBookedBy;
	private String  travelDate;
	private String  departureLocation;
	private String  arrivalLocation;
	private String  preferredTimeBefore;
	private String  preferredTimeArrival;
	private String  carrierDetails;
	private String  modeOfPayment;
	private String  currency;
	private String  amount;
	private String  approvedAmount;
	private String  createdBy;
	private String  remarks;
	private String  cashDate;
	private String  statusMessage;
	private int status;
    private String approvalRemarks;
    private String approvedBy;
	private Float approvalAmount;
	private String approvedOrRejected;
}
