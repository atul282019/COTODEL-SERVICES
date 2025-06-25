package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicenceMaster {

	private Long id;
    private String licence_key ;
    private String valid_form;
    private String valid_to;
    private int  status ;
    private String created_by;
    private String created_date;
    private String updated_by ;
    private String updated_date;
    private String enc_key;
    private int employer_id;

}
