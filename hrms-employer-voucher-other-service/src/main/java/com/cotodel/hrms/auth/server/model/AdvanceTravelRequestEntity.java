package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
	
	@Column(name="amount",scale = 2)
	private Float amount;
	
	@Column(name="approved_amount",scale = 2)
	private Float approvedAmount;	

	@Column(name="created_date")
	private LocalDateTime  createdDate ;	

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
	
	@Column(name="travel_sub_type")
	private String travelSubType;
	
	@Column(name="date")
	private LocalDate date;
	
	@Column(name="departure")
	private String departure;
	
	@Column(name="arrival")
	private String arrival;
	
	@Column(name="preferred_time")
	private String preferredTime;
	
	@Column(name="time_preference")
	private String timePreference;
	
	@Column(name="carrier_class")
	private String carrierClass;
	
	@Column(name="payment_mode")
	private String paymentMode;
	
	@Column(name="hotel_details")
	private String hotelDetails;
	
	@Column(name="location")
	private String location;

	@Column(name="checkout_date")
	private LocalDate  checkoutDate;
	
	@Column(name="checkin_date")
	private LocalDate  checkinDate;
	
	@Column(name="type")
	private String type;
	
	@Column(name="from_location")
	private String fromLocation;
	
	@Column(name="to_location")
	private String toLocation;

	@Column(name="type_of_meal")
	private String typeOfMeal;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="number_of_days")
	private String numberOfDays;
	
	@Column(name="approved_status")
	private int approvedStatus;//0-1-2
	
	@Column(name="approved_status_remarks")
	private String approvedStatusRemarks;//0-for pending,1-approved,2-rejected
	
	@Column(name="status_remarks")
	private String statusRemarks;//-Draft,Submitted
	
	@Column(name="arrival_preference")
	private String arrivalPreference;//-Draft,Submitted
	
	@Column(name="limit_amount")
	private String limitAmount;//-Draft,Submitted
	
	@Column(name="title")
	private String title;//-Draft,Submitted
	
}
