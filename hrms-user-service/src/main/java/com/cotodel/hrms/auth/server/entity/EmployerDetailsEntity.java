package com.cotodel.hrms.auth.server.entity;

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
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employer_details",uniqueConstraints = {@UniqueConstraint(columnNames = {"mobile"}),
        @UniqueConstraint(columnNames = {"gst_identification_number"})})
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employer_details_seq" , sequenceName="employer_details_seq", allocationSize=1)
public class EmployerDetailsEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employer_details_seq")
	private Long id;
	
	@Column(name="employer_id")
	private Long employerId;
	
	@Column(name="employer_code")
	private String employerCode;
	
	@Column(name="legal_name_of_business")
	private String legalNameOfBusiness;
	
	@Column(name="trade_name")
	private String tradeName;
	
	@Column(name="constitution_of_business")
	private String constitutionOfBusiness;
	
	@Column(name="org_type")
	private String orgType;
	
	@Column(name="address1")
	private String address1;
	
	@Column(name="address2")
	private String address2;
	
	@Column(name="district_name")
	private String districtName;
	
	@Column(name="pincode")
	private String pincode;
	
	@Column(name="state_name")
	private String stateName;
	
	@Column(name="consent")
	private String consent;
	
	@Column(name="street_name")
	private String streetName;
	
	@Column(name="building_number")
	private String buildingNumber;	

	@Column(name="building_name")
	private String buildingName;
	
	@Column(name="location")
	private String location;	
	
	@Column(name="floor_number")
	private String floorNumber;	
	
	@Column(name="nature_of_principal_place_of_business")
	private String natureOfPrincipalPlaceOfBusiness;
	
	@Column(name="center_jurisdiction_code")
	private String centerJurisdictionCode;
	
	@Column(name="gst_identification_number")
	private String gstIdentificationNumber;
	
	@Column(name="pan")
	private String pan;	

	@Column(name="profile_complete")
	private int profileComplete;
	
	@Column(name="otp_status")
	private String otpStatus;

	@Column(name="status")
	private int status;
	
	@Column(name="created_date")
	private LocalDate createdDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_date")
	private LocalDate updatedDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="organization_name")
	private String organizationName;
		 
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="email")
	private String email;
	
	@Column(name="name")
	private String name;
	
	@Column(name="company_id")
	private String companyId;
	
	@Column(name="hrms_id")
	private String hrmsId;
	
	@Column(name="company_size")
	private String companySize;
	
	@Column(name = "erupistatus")
	private boolean erupistatus;
	private String privacyCheck;	
	private String whatsupCheck;
	
	@Column(name="organization_type")
	private String organizationType;
	
	@Column(name="company_type")
	private String companyType;
	
	
}

