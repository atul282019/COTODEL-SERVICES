package com.cotodel.hrms.auth.server.model.vehicle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class VehicleSequenceEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_sequence_generator")
    @SequenceGenerator(name = "vehicle_sequence_generator", sequenceName = "vehicle_sequence", allocationSize = 1)
    private Long id;

    private String name;

}
