package com.cotodel.hrms.auth.server.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingDao;
import com.cotodel.hrms.auth.server.dao.VehicleDriverManagementDao;
import com.cotodel.hrms.auth.server.dao.VehicleManagementDao;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementGetDto;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementSaveRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementTripRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleTripDto;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleDriverManagementEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;
import com.cotodel.hrms.auth.server.repository.vehicle.VehicleSequenceRepository;
import com.cotodel.hrms.auth.server.service.VehicleManagementService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class VehicleManagementServiceImpl implements VehicleManagementService{

	@Autowired
	VehicleManagementDao  vehicleManagementDao;
	
	@Autowired
	VehicleDriverManagementDao  vehicleDriverManagementDao;
	
	@Autowired
	VehicleSequenceRepository  vehicleSequenceRepository;
	
	@Autowired
	EmployeeOnboardingDao  employeeOnboardingDao;

	@Override
	public VehicleManagementSaveRequest saveVehicleManagement(VehicleManagementSaveRequest request) {
		String response = "";
		VehicleManagementEntity vehicleManagementEntity = null;
		try {
			if(request.getOrgId()==null) {
				request.setResponse(MessageConstant.ORGNULL);
				return request;
			}else if(request.getVehicleNumber()==null) {
				request.setResponse(MessageConstant.VEHICLENULL);
				return request;
			}
			String vehicleNo=request.getVehicleNumber()==null?"":request.getVehicleNumber().toUpperCase();
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
			vehicleManagementEntity = new VehicleManagementEntity();
			CopyUtility.copyProperties(request, vehicleManagementEntity);
			vehicleManagementEntity.setStatus(1);
			vehicleManagementEntity.setVehicleNumber(vehicleNo);			
			String vehicleSequenceId= vehicleSequenceId();
			vehicleManagementEntity.setCreationDate(LocalDateTime.now());
			vehicleManagementEntity.setVehicleSequenceId(vehicleSequenceId);
			vehicleManagementEntity = vehicleManagementDao.saveVehicleManagementDetails(vehicleManagementEntity);
			response = MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);

		}catch (DataIntegrityViolationException ex) {
			request.setResponse(MessageConstant.VEHICLUNIQUE);
		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			// e.printStackTrace();
			request.setResponse(response);
		}
		return request;
	}

	@Override
	public List<VehicleManagementGetDto> getVehicleManagement(VehicleManagementRequest vehicleManagementRequest) {
		
		List<VehicleManagementEntity> vehicleList=vehicleManagementDao.getVehicleManagement(vehicleManagementRequest.getOrgId());
		List<VehicleManagementGetDto> finalList=new ArrayList<VehicleManagementGetDto>();
		int count=0;
		if(vehicleManagementRequest.getLimit()!=null && vehicleManagementRequest.getLimit().equalsIgnoreCase("Yes")) {
		for (VehicleManagementEntity vehicleManagementEntity : vehicleList) {
			VehicleManagementGetDto vehicleManagementGetDto=new VehicleManagementGetDto(); 
			vehicleManagementGetDto.setAssignedDriver("NO");
			vehicleManagementGetDto.setTripStatus("Not yet Started");
			CopyUtility.copyProperties(vehicleManagementEntity, vehicleManagementGetDto);
			
			Object[] vehicleTripDto= vehicleDriverManagementDao.getVehicleTrip(vehicleManagementEntity.getId());
			if(vehicleTripDto!=null && vehicleTripDto.length > 0) {
				try {
					Object obj=vehicleTripDto[0];
					Object[] row = (Object[]) obj;
					Timestamp timestamp = (Timestamp) row[0];
					LocalDateTime tripStartDate = timestamp.toLocalDateTime();
					Timestamp timestamp1 = (Timestamp) row[1];
					LocalDateTime endDateTime = timestamp1.toLocalDateTime();
					vehicleManagementGetDto.setStartDate(tripStartDate.toLocalDate());
					vehicleManagementGetDto.setEndDate(endDateTime.toLocalDate());
					System.out.println("endDateTime:::"+endDateTime);
					long daysBetween = ChronoUnit.DAYS.between(tripStartDate, endDateTime);
					vehicleManagementGetDto.setNoOfDays(String.valueOf(daysBetween));
					if(daysBetween<0) {
						vehicleManagementGetDto.setAssignedDriver("NO");
						vehicleManagementGetDto.setTripStatus("Completed");
					}else {
						vehicleManagementGetDto.setAssignedDriver("Yes");
						vehicleManagementGetDto.setTripStatus("In progress");
						
					}
					String driverName = (String) row[2];
					String driverMobile = (String) row[3];
					String clientName = (String) row[5];
					vehicleManagementGetDto.setDriverName2(driverName);
					vehicleManagementGetDto.setDriverMobile(driverMobile);
					vehicleManagementGetDto.setClientName(clientName);
					System.out.println(daysBetween);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			finalList.add(vehicleManagementGetDto);
			count++;
			if(count==10) {
				break;
			}
		}
		}else {
			for (VehicleManagementEntity vehicleManagementEntity : vehicleList) {
				VehicleManagementGetDto vehicleManagementGetDto=new VehicleManagementGetDto(); 
				vehicleManagementGetDto.setAssignedDriver("NO");
				vehicleManagementGetDto.setTripStatus("Not yet Started");
				CopyUtility.copyProperties(vehicleManagementEntity, vehicleManagementGetDto);
				
				Object[] vehicleTripDto= vehicleDriverManagementDao.getVehicleTrip(vehicleManagementEntity.getId());
				if(vehicleTripDto!=null && vehicleTripDto.length > 0) {
					try {
						Object obj=vehicleTripDto[0];
						Object[] row = (Object[]) obj;
						Timestamp timestamp = (Timestamp) row[0];
						LocalDateTime tripStartDate = timestamp.toLocalDateTime();
						Timestamp timestamp1 = (Timestamp) row[1];
						LocalDateTime endDateTime = timestamp1.toLocalDateTime();
						vehicleManagementGetDto.setStartDate(tripStartDate.toLocalDate());
						vehicleManagementGetDto.setEndDate(endDateTime.toLocalDate());
						System.out.println("endDateTime:::"+endDateTime);
						long daysBetween = ChronoUnit.DAYS.between(tripStartDate, endDateTime);
						vehicleManagementGetDto.setNoOfDays(String.valueOf(daysBetween));
						if(daysBetween<0) {
							vehicleManagementGetDto.setAssignedDriver("NO");
							vehicleManagementGetDto.setTripStatus("Completed");
						}else {
							vehicleManagementGetDto.setAssignedDriver("Yes");
							vehicleManagementGetDto.setTripStatus("In progress");
							
						}
						String driverName = (String) row[2];
						String driverMobile = (String) row[3];
						String clientName = (String) row[5];
						vehicleManagementGetDto.setDriverName2(driverName);
						vehicleManagementGetDto.setDriverMobile(driverMobile);
						vehicleManagementGetDto.setClientName(clientName);
						System.out.println(daysBetween);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				finalList.add(vehicleManagementGetDto);
				
			}
		}
		
		return finalList;
	}

//	@Override
//	public VehicleManagementTripRequest saveTripManagement(VehicleManagementTripRequest request) {
//		String response = "";
//		VehicleDriverManagementEntity vehicleDriverManagementEntity = null;
//		try {
//			if(request.getOrgId()==null) {
//				request.setResponse(MessageConstant.ORGNULL);
//			}
//			response = MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//			vehicleDriverManagementEntity = new VehicleDriverManagementEntity();
//			CopyUtility.copyProperties(request, vehicleDriverManagementEntity);
//			vehicleDriverManagementEntity.setStatus(1);
//			vehicleDriverManagementEntity.setCreationDate(LocalDateTime.now());
//			VehicleManagementEntity vehicleId=new VehicleManagementEntity();
//			vehicleId.setId(request.getVehicleId());
//			vehicleDriverManagementEntity.setVehicleId(vehicleId);
//			vehicleDriverManagementEntity = vehicleDriverManagementDao.saveVehicleTripManagementDetails(vehicleDriverManagementEntity);
//			response = MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//
//		} catch (Exception e) {
//			response = MessageConstant.RESPONSE_FAILED;
//			// e.printStackTrace();
//			request.setResponse(response);
//		}
//		return request;
//	}

	@Override
	public List<VehicleTripDto> getVehicleTripManagement(String sequenceId) {
		List<VehicleTripDto> vehicleList=null;
		VehicleManagementEntity vehicleMange=vehicleManagementDao.getVehicleManagementBySequenceId(sequenceId);
		if(vehicleMange!=null) {
		vehicleList=vehicleDriverManagementDao.getVehicleTripList(vehicleMange.getId());
		}
		return vehicleList;
	}

	@Override
	public VehicleManagementEntity getVehicleManagementById(String vehicleSequenceId) {
		// TODO Auto-generated method stub
		return vehicleManagementDao.getVehicleManagementBySequenceId(vehicleSequenceId);
	}

	@Override
	public VehicleManagementTripRequest tripVehicleDetails(VehicleManagementTripRequest request) {
		String response = "";
		VehicleManagementEntity vehicleManagementEntity = null;
		response = MessageConstant.RESPONSE_FAILED;
		request.setResponse(response);
		try {
			if(request.getOrgId()==null) {
				request.setResponse(MessageConstant.ORGNULL);
				return request;
			}else if(request.getVehicleSequenceId()==null) {
				request.setResponse(MessageConstant.SEQUENCEIDNULL);
				return request;
			}else if(request.getDriverId()==null) {
				request.setResponse(MessageConstant.DRIVERIDNULL);
				return request;
			}
			VehicleDriverManagementEntity vehicleDriverManagementEntity = null;
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
			
			vehicleManagementEntity=vehicleManagementDao.getVehicleManagementBySequenceId(request.getVehicleSequenceId());
			if(vehicleManagementEntity!=null) {
			
				response = MessageConstant.RESPONSE_FAILED;
				request.setResponse(response);
				String validate=checkTripValidate(vehicleManagementEntity.getId());
				String[] valid=validate.split(",");
				String checkValid=valid[0];
				String expDate=valid[1];
				if (checkValid.equalsIgnoreCase("Yes")) {	
					
					vehicleDriverManagementEntity = new VehicleDriverManagementEntity();
					CopyUtility.copyProperties(request, vehicleDriverManagementEntity);
					vehicleDriverManagementEntity.setStatus(1);
					Long driverid=Long.valueOf(request.getDriverId());
					vehicleDriverManagementEntity.setDriverId(driverid);
					vehicleDriverManagementEntity.setCreationDate(LocalDateTime.now());
					LocalDateTime localDateTime=LocalDateTime.now();
					vehicleDriverManagementEntity.setTripStartDate(localDateTime);
					String validity="";
//					if(request.getSelectedTimeperiod().equalsIgnoreCase("Tagged until changed")) {
//						validity=request.getCustomTimePeriod();
//					}else {
//						
//					}
					validity=request.getSelectedTimeperiod();
					String regex = "^\\d+ Days$";
			        boolean matches = validity.matches(regex);
			        if (matches) {
			            System.out.println("Valid format.");
			        } else {
			            System.out.println("Invalid format.");
			            response = MessageConstant.INVALIDDAYS;
						request.setResponse(response);
						return request;
			            
			        }
			        List<VehicleDriverManagementEntity> checkDriver=vehicleDriverManagementDao.getTripsEndingTodayOrLater(driverid);
			        if(checkDriver!=null && checkDriver.size()>0) {
			        	response = MessageConstant.TRIPINVALID;
			        	request.setResponse(response);
			        	VehicleDriverManagementEntity ent=checkDriver.get(0);
			        	if(ent!=null) {
			        	LocalDateTime tripEndDate = ent.getTripEndDate(); // returns LocalDate
			        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // or any format you prefer
			        	String formattedDate = tripEndDate.format(formatter);
			        	String updatedMessage = response.replace("expDate", formattedDate);
						request.setResponse(updatedMessage);
			        	}
						return request;
			        }
					String[] daysArray=validity.split(" ");
					Long days=Long.valueOf(daysArray[0]);
					LocalDateTime etDate = null;
					if (localDateTime != null) {
					    etDate = localDateTime.plusDays(days);  // Add the days to the start date
					}
					vehicleDriverManagementEntity.setTripEndDate(etDate);
					
					vehicleDriverManagementEntity.setVehicleNo(vehicleManagementEntity.getVehicleNumber());
//					VehicleManagementEntity vehicleId=new VehicleManagementEntity();
//					vehicleId.setId(vehicleManagementEntity.getId());
					vehicleDriverManagementEntity.setVehicleId(vehicleManagementEntity);
					vehicleDriverManagementEntity = vehicleDriverManagementDao.saveVehicleTripManagementDetails(vehicleDriverManagementEntity);
					response = MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);
//					EmployeeOnboardingEntity empl=employeeOnboardingDao.getEmployeeOnboardingId(vehicleDriverManagementEntity.getDriverId());
//					if(empl!=null) {
//						empl.setDriverAssignFlag("Y");
//						employeeOnboardingDao.saveDetails(empl);
//					}
					
				}else {
					response = MessageConstant.TRIPINVALID;
					response=response.replace("expDate", expDate);
					
					request.setResponse(response);
				}
			}
		}catch (DataIntegrityViolationException ex) {
			request.setResponse(MessageConstant.VEHICLUNIQUE);
		} catch (Exception e) {
			e.printStackTrace();
			response = MessageConstant.RESPONSE_FAILED;
			// e.printStackTrace();
			request.setResponse(response);
		}
		return request;
	}

	public String checkTripValidate(Long id) {
		String tripValid="Yes"+",No";
		Object[] vehicleTripDto= vehicleDriverManagementDao.getVehicleTrip(id);
		if(vehicleTripDto!=null && vehicleTripDto.length > 0) {
			try {
				Object obj=vehicleTripDto[0];
				Object[] row = (Object[]) obj;
				Timestamp timestamp = (Timestamp) row[0];
				LocalDateTime tripStartDate = timestamp.toLocalDateTime();
				Timestamp timestamp1 = (Timestamp) row[1];
				LocalDateTime endDateTime = timestamp1.toLocalDateTime();
				//vehicleManagementGetDto.setStartDate(tripStartDate.toLocalDate());
				//vehicleManagementGetDto.setEndDate(endDateTime.toLocalDate());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		        String formattedDate = endDateTime.format(formatter);
				System.out.println("endDateTime:::"+endDateTime);
				long daysBetween = ChronoUnit.DAYS.between(tripStartDate, endDateTime);
				if(daysBetween<0) {
					tripValid="Yes"+",No";
				}else {
					tripValid="NO,"+formattedDate;					
				}
				System.out.println(daysBetween);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return tripValid;
	}
	public Long fetchNextSequenceValue() {
	    return vehicleSequenceRepository.getNextSeriesId();
	 }
	public String vehicleSequenceId() {
		
		String  sequence=String.valueOf(fetchNextSequenceValue());
		
		String sequenceValue="";
		String finalSequenceValue="";
		
		if(sequence.length()==1) {
			sequenceValue="00"+sequence;
		}else if(sequence.length()==2) {
			sequenceValue="0"+sequence;
		}else {
			sequenceValue=sequence;
		}
		
		finalSequenceValue=monthYear()+"-"+sequenceValue;
		
		return finalSequenceValue;
	}
	public String monthYear() {
		
		LocalDate date = LocalDate.now();		
		String month=String.valueOf(date.getMonthValue());
		String year=String.valueOf(date.getYear());
		String str="VM-";
		String  monthValue=month.length()==1?"0"+month:month;
		String  yearValue=year.substring(2,4);
		
		return str+monthValue+yearValue;
	}
}
