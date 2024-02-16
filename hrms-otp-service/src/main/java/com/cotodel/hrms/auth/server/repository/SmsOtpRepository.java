package com.cotodel.hrms.auth.server.repository;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import org.springframework.data.repository.CrudRepository;

import com.cotodel.hrms.auth.server.model.SmsOtpEntity;

public interface SmsOtpRepository extends CrudRepository<SmsOtpEntity, Integer> {

}
