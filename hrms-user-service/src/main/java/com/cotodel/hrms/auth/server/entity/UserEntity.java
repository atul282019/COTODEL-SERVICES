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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_details", uniqueConstraints = {@UniqueConstraint(columnNames = "mobile"),@UniqueConstraint(columnNames = "employeeId")})
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="user_details_id_seq" , sequenceName="user_details_id_seq", allocationSize=1)
public class UserEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_details_id_seq")
	private Long id ;
	
    private String first_name;
    private String last_name;
    private String dateofbirth ;
    private String  gender;
    private String contact_number;
    @Column(unique=true)
    private String email ;
    private String address ;
    private String org_type;
    private String  org_name;
    @Column(unique=true,nullable = false)
    private String  mobile ;
    private LocalDate  created_date ;
    private int  email_verify_status;
    private int  mobile_verify_status;
    private LocalDate email_verify_date;
    private LocalDate mobile_verify_date;
    private String username;
    private String pwd ;
    private int status ;
    private int employerid ;
    private int role_id ;
    private String createdBy ;
    @Column(name="mapper_flag",length =1)
	private String mapperFlag ;
    @Transient
    private String response;
    @Column(name="company_size")
	private String companySize;
    private String role;
    private String companyId;
    private String hrmsId;
    private String hrmsName;
    private String employeeId;
	private String managerEmployeeId;
	@Column(name="company_type")
	private String companyType;
	@Transient
    private String organizationName;
//    @OneToMany
//   	@JoinTable(name = "h_user_emp", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "user"))
//    private List<UserEmpEntity> userEmpEntity ;
    //@OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL, orphanRemoval = true)
   // private List<UserEmpEntity> userEmpEntity;
   // @OneToMany
//    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="user")
//    private List<UserEmpEntity> userEmpEntity ;
    
}
