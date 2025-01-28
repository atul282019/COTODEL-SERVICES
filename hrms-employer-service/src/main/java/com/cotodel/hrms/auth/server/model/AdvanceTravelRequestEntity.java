package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="advance_travel_request")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="advance_travel_request_seq" , sequenceName="advance_travel_request_seq", allocationSize=1)

public class AdvanceTravelRequestEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="advance_travel_request_seq")	
	@Column(name="id")
	private Long id;
	
	@Column(name = "employer_id")
    private Long employerId;
	
	@Column(name = "employee_id")
    private Long employeeId;
	
	@Column(name="request_type")
	private String requestType;
	
	@Column(name="expense_category_id")
	private String expenseCategoryId;
	
	@Column(name="expense_category")
	private String expenseCategory;
	
	@Column(name="mode")
	private String mode;
	
	@Column(name="to_be_booked_by")
	private String toBeBookedBy;
	
	@Column(name="travel_date")
	private LocalDate travelDate;
	
	@Column(name="departure_location")
	private String departureLocation;
	
	@Column(name="arrival_location")
	private String arrivalLocation;
	
	@Column(name="preferred_time_before")
	private String preferredTimeBefore;
	
	@Column(name="preferred_time_arrival")
	private String preferredTimeArrival;
	
	@Column(name="carrier_details")
	private String carrierDetails;
	
	@Column(name="mode_of_payment")
	private String modeOfPayment;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="amount")
	private String amount;
	
	@Column(name="approved_amount")
	private String approvedAmount;	

	@Column(name="created_date")
	private LocalDate  createdDate ;	

	@Column(name="created_by")
	private String createdBy;	
	
	@Column(name="approved_date")
	private LocalDate approvedDate;
	
	@Column(name="approved_by")
	private String approvedBy;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name = "status")
    private int status;
	
	@Column(name = "sequence_id")
	private String sequenceId;
	
	@Column(name="cash_date")
	private LocalDate cashDate;
	
	
}
