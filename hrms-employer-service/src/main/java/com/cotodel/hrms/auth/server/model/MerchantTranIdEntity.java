package com.cotodel.hrms.auth.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class MerchantTranIdEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "merchant_tran_id")
    @SequenceGenerator(name = "merchant_tran_id", sequenceName = "merchantTranId", allocationSize = 1)
    private Long id;

    private String name;

}
