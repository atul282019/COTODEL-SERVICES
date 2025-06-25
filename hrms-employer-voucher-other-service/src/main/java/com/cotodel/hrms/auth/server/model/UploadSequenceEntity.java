package com.cotodel.hrms.auth.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class UploadSequenceEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "upload_sequence_generator")
    @SequenceGenerator(name = "upload_sequence_generator", sequenceName = "upload_sequence", allocationSize = 1)
    private Long id;

    private String name;

}
