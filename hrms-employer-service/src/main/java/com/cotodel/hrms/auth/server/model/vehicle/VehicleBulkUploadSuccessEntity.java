package com.cotodel.hrms.auth.server.model.vehicle;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="vehicle_bulk_upload_success",indexes = {@Index(name = "idx_success_orgid_filename", columnList = "org_id, file_name")})
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="vehicle_bulk_upload_success_seq" , sequenceName="vehicle_bulk_upload_success_seq", allocationSize=1)
public class VehicleBulkUploadSuccessEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="vehicle_bulk_upload_success_seq")
	private Long id;
	
	@Column(name="vehicle_no")
	private String vehicleNumber;
	
	@Column(name="file_name")
	private String fileName;	

	@Column(name = "create_flag")
    private String createFlag="N";
	
	@Column(name = "org_id")
    private Long orgId;
	
	@Column(name = "creation_date")
    private LocalDateTime creationDate;
	
	@Column(name="createdby", length=49)
	private String createdby;
		
	@Column(name="bulktbl_id")
	private Long bulktblId;

	@Column(name="status")
	private Long status;
	
	@Column(name="client_id")
	private String client_id;
	
	@Column(name="rc_number")
	private String rc_number;
	
	@Column(name="fit_up_to")
	private String fit_up_to;
	
	@Column(name="registration_date")
	private String registration_date;
	
	@Column(name="owner_name")
	private String owner_name;
	
	@Column(name="father_name")
	private String father_name;
	
	@Column(name="present_address")
	private String present_address;
	
	@Column(name="permanent_address")
	private String permanent_address;
	
	@Column(name="mobile_number")
	private String mobile_number;
	
	@Column(name="vehicle_category")
	private String vehicle_category;
	
	@Column(name="vehicle_chasi_number")
	private String vehicle_chasi_number;
	
	@Column(name="vehicle_engine_number")
	private String vehicle_engine_number;
	
	@Column(name="maker_description")
	private String maker_description;
	
	@Column(name="maker_model")
	private String maker_model;
	
	@Column(name="body_type")
	private String body_type;
	
	@Column(name="fuel_type")
	private String fuel_type;
	
	@Column(name="color")
	private String color;
	
	@Column(name="norms_type")
	private String norms_type;
	
	@Column(name="financer")
	private String financer;
	
	@Column(name="financed")
	private boolean financed;
	
	@Column(name="insurance_company")
	private String insurance_company;
	
	@Column(name="insurance_policy_number")
	private String insurance_policy_number;
	
	@Column(name="insurance_upto")
	private String insurance_upto;
	
	@Column(name="manufacturing_date")
	private String manufacturing_date;
	
	@Column(name="manufacturing_date_formatted")
	private String manufacturing_date_formatted;
	
	@Column(name="registered_at")
	private String registered_at;
	
	@Column(name="latest_by")
	private String latest_by;
	
	@Column(name="less_info")
	private boolean less_info;
	
	@Column(name="tax_upto")
	private String tax_upto;
	
	@Column(name="tax_paid_upto")
	private String tax_paid_upto;
	
	@Column(name="cubic_capacity")
	private String cubic_capacity;
	
	@Column(name="vehicle_gross_weight")
	private String vehicle_gross_weight;
	
	@Column(name="no_cylinders")
	private String no_cylinders;
	
	@Column(name="seat_capacity")
	private String seat_capacity;
	
	@Column(name="sleeper_capacity")
	private String sleeper_capacity;
	
	@Column(name="standing_capacity")
	private String standing_capacity;
	
	@Column(name="wheelbase")
	private String wheelbase;
	
	@Column(name="unladen_weight")
	private String unladen_weight;
	
	@Column(name="vehicle_category_description")
	private String vehicle_category_description;
	
	@Column(name="pucc_number")
	private String pucc_number;
	
	@Column(name="pucc_upto")
	private String pucc_upto;
	
	@Column(name="permit_number")
	private String permit_number;
	
	@Column(name="permit_issue_date")
	private String permit_issue_date;
	
	@Column(name="permit_valid_from")
	private String permit_valid_from;
	
	@Column(name="permit_valid_upto")
	private String permit_valid_upto;
	
	@Column(name="permit_type")
	private String permit_type;
	
	@Column(name="national_permit_number")
	private String national_permit_number;
	
	@Column(name="national_permit_upto")
	private String national_permit_upto;
	
	@Column(name="national_permit_issued_by")
	private String national_permit_issued_by;
	
	@Column(name="non_use_status")
	private String non_use_status;
	
	@Column(name="non_use_from")
	private String non_use_from;
	
	@Column(name="non_use_to")
	private String non_use_to;
	
	@Column(name="blacklist_status")
	private String blacklist_status;
	
	@Column(name="noc_details")
	private String noc_details;
	
	@Column(name="owner_number")
	private String owner_number;
	
	@Column(name="rc_status")
	private String rc_status;
	
	@Column(name="masked_name")
	private boolean masked_name;
	
	@Column(name="challan_details")
	private String challan_details;

	@Column(name="variant")
	private String variant;
	
	@Column(name="rto_code")
	private String rto_code;
	
}
