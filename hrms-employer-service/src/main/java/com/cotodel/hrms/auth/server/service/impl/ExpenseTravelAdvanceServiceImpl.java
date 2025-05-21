package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.AdvanceTravelRequestDao;
import com.cotodel.hrms.auth.server.dao.ExpenseTravelAdvanceDao;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelAllRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelAllUpdateRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelCashRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelDto;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelRequest;
import com.cotodel.hrms.auth.server.dto.ApprovalTravelReimbursement;
import com.cotodel.hrms.auth.server.dto.ExpanceTravelAdvance;
import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceRequest;
import com.cotodel.hrms.auth.server.dto.TravelGetReimbursement;
import com.cotodel.hrms.auth.server.dto.TravelReimbursement;
import com.cotodel.hrms.auth.server.dto.TravelRequestUpdate;
import com.cotodel.hrms.auth.server.model.AdvanceRequestSettingEntity;
import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;
import com.cotodel.hrms.auth.server.repository.UploadSequenceRepository;
import com.cotodel.hrms.auth.server.service.ExpenseTravelAdvanceService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.ValidateConstants;
@Repository
public class ExpenseTravelAdvanceServiceImpl implements ExpenseTravelAdvanceService{

	@Autowired
	ExpenseTravelAdvanceDao  expenseTravelAdvanceDao;
	
	@Autowired
	AdvanceTravelRequestDao  advanceTravelRequestDao;
	
	@Autowired
	UploadSequenceRepository uploadSequenceRepository;

	@Override
	public ExpenseTravelAdvanceRequest saveExpenseTravelAdvenceDetails(ExpenseTravelAdvanceRequest request) {
		String response="";
		AdvanceRequestSettingEntity employeeBandEntity=null;
		AdvanceRequestSettingEntity employeeBandEntity1=null;
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			
			String dataString = request.getEmployerId()+request.getAllowEmployeesTravel()
		       +request.getAllowEmployeesCash()+request.getEmployeesAllow()
		       +request.getNameEmployeesCash()+request.getDaysDisbursalCash()+
		       request.getClientKey()+MessageConstant.SECRET_KEY;
			
			String hash=ValidateConstants.generateHash(dataString);
			if(!request.getHash().equalsIgnoreCase(hash)) {
				request.setResponse(MessageConstant.HASH_ERROR);
				return request;
			}
			
			employeeBandEntity=new AdvanceRequestSettingEntity();
			
			employeeBandEntity=expenseTravelAdvanceDao.findByEmployerId(request.getEmployerId());
			if(employeeBandEntity!=null) {
				employeeBandEntity1=new AdvanceRequestSettingEntity();
				CopyUtility.copyProperties(request,employeeBandEntity1);
				employeeBandEntity1.setStatus(1l);
				employeeBandEntity1.setId(employeeBandEntity.getId());
				employeeBandEntity1.setCreated_date(employeeBandEntity.getCreated_date());
				Date date = new Date();
				LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				employeeBandEntity1.setModified_date(localDate);
				
				employeeBandEntity1=expenseTravelAdvanceDao.saveDetails(employeeBandEntity1);
				response=MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			}else {
				employeeBandEntity=new AdvanceRequestSettingEntity();
				CopyUtility.copyProperties(request,employeeBandEntity);			
				employeeBandEntity.setStatus(1l);
				Date date = new Date();
				LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				employeeBandEntity.setCreated_date(localDate);
				employeeBandEntity=expenseTravelAdvanceDao.saveDetails(employeeBandEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			}
			
			
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			//e.printStackTrace();
			request.setResponse(response);
		}
		return request;
	}

//	@Override
//	public AdvanceRequestSettingEntity getExpenseTravelAdvenceDetails(Long employerid) {
//		AdvanceRequestSettingEntity expanceTravelAdvanceEntities=null;
//		String response="";
//		try {
//			expanceTravelAdvanceEntities=expenseTravelAdvanceDao.findByEmployerId(employerid);
//			
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			//e.printStackTrace();
//			//request.setResponse(response);
//		}
//		return expanceTravelAdvanceEntities;
//	}

	@Override
	public ExpanceTravelAdvance getExpenseTravelAdvence(Long employerid) {
		AdvanceRequestSettingEntity expanceTravelAdvanceEntities=new AdvanceRequestSettingEntity();
		ExpanceTravelAdvance expanceTravelAdvance=new ExpanceTravelAdvance();
		String response="";
		List<String> list=new ArrayList<String>();
		try {
			expanceTravelAdvanceEntities=expenseTravelAdvanceDao.findByEmployerId(employerid);
			if(expanceTravelAdvanceEntities!=null) {
			CopyUtility.copyProperties(expanceTravelAdvanceEntities,expanceTravelAdvance);
			String name=expanceTravelAdvanceEntities.getNameEmployeesCash();
			if(name!=null) {
				String [] nameOfArray=name.split(",");
				for (String string : nameOfArray) {
					list.add(string);
				}
			}
			expanceTravelAdvance.setNameEmployeesCash(list);
			response=MessageConstant.RESPONSE_SUCCESS;
			}
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			//e.printStackTrace();
		}
		return expanceTravelAdvance;
	}
	
	
	
	@Override
	public AdvanceTravelCashRequest saveAdvenceTravelRequestCashDetails(AdvanceTravelCashRequest request) {
		// TODO Auto-generated method stub
		String response ="";
		try {
			AdvanceTravelRequestEntity advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
			CopyUtility.copyProperties(request, advanceTravelRequestEntity);
			advanceTravelRequestEntity.setStatus(1);
			advanceTravelRequestEntity.setStatusRemarks("Submitted");
			advanceTravelRequestEntity.setCreatedDate(LocalDateTime.now());
			String amt=request.getAmount();
			Float amount=Float.valueOf(amt);
			advanceTravelRequestEntity.setAmount(amount);
			String sequenceId= sequenceID();
			advanceTravelRequestEntity.setSequenceId(sequenceId);
			advanceTravelRequestEntity.setPaymentMode(request.getModeOfPayment());
			advanceTravelRequestEntity = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
			response = MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
			request.setId(advanceTravelRequestEntity.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return request;
	}

	@Override
	public AdvanceTravelRequest saveAdvenceTravelRequestDetails(AdvanceTravelRequest request) {
		String response = "";
		AdvanceTravelRequestEntity advanceTravelRequestEntity = null;
		try {
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
			if(request.getRequestType().equalsIgnoreCase("Travel")) {
				List<TravelReimbursement> travelReimbursement=request.getTravelReimbursement();
				for (TravelReimbursement travelReimbursement2 : travelReimbursement) {
					advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
					CopyUtility.copyProperties(travelReimbursement2, advanceTravelRequestEntity);
					advanceTravelRequestEntity.setEmployeeId(request.getEmployeeId());
					advanceTravelRequestEntity.setEmployerId(request.getEmployerId());
					advanceTravelRequestEntity.setRequestType(request.getRequestType());
					advanceTravelRequestEntity.setCreatedDate(LocalDateTime.now());
					advanceTravelRequestEntity.setStatus(0);
					String sequenceId= sequenceID();
					advanceTravelRequestEntity.setSequenceId(sequenceId);
					advanceTravelRequestEntity.setStatusRemarks("Draft");
					advanceTravelRequestEntity = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
				}
				response = MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			}else if(request.getRequestType().equalsIgnoreCase("Accomodation")) {
				List<TravelReimbursement> travelReimbursement=request.getTravelReimbursement();
				for (TravelReimbursement travelReimbursement2 : travelReimbursement) {
					advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
					CopyUtility.copyProperties(travelReimbursement2, advanceTravelRequestEntity);
					advanceTravelRequestEntity.setEmployeeId(request.getEmployeeId());
					advanceTravelRequestEntity.setEmployerId(request.getEmployerId());
					advanceTravelRequestEntity.setRequestType(request.getRequestType());
					advanceTravelRequestEntity.setCreatedDate(LocalDateTime.now());
					advanceTravelRequestEntity.setStatus(0);
					String sequenceId= sequenceID();
					advanceTravelRequestEntity.setSequenceId(sequenceId);
					advanceTravelRequestEntity.setStatusRemarks("Draft");
					advanceTravelRequestEntity = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
				}
				response = MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			}else if(request.getRequestType().equalsIgnoreCase("In-City-Cab")) {
				List<TravelReimbursement> travelReimbursement=request.getTravelReimbursement();
				for (TravelReimbursement travelReimbursement2 : travelReimbursement) {
					advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
					CopyUtility.copyProperties(travelReimbursement2, advanceTravelRequestEntity);
					advanceTravelRequestEntity.setEmployeeId(request.getEmployeeId());
					advanceTravelRequestEntity.setEmployerId(request.getEmployerId());
					advanceTravelRequestEntity.setRequestType(request.getRequestType());
					advanceTravelRequestEntity.setCreatedDate(LocalDateTime.now());
					advanceTravelRequestEntity.setStatus(0);
					String sequenceId= sequenceID();
					advanceTravelRequestEntity.setSequenceId(sequenceId);
					advanceTravelRequestEntity.setStatusRemarks("Draft");
					advanceTravelRequestEntity = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
				}
				response = MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			}else if(request.getRequestType().equalsIgnoreCase("Meal")) {
				List<TravelReimbursement> travelReimbursement=request.getTravelReimbursement();
				for (TravelReimbursement travelReimbursement2 : travelReimbursement) {
					advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
					CopyUtility.copyProperties(travelReimbursement2, advanceTravelRequestEntity);
					advanceTravelRequestEntity.setEmployeeId(request.getEmployeeId());
					advanceTravelRequestEntity.setEmployerId(request.getEmployerId());
					advanceTravelRequestEntity.setRequestType(request.getRequestType());
					advanceTravelRequestEntity.setCreatedDate(LocalDateTime.now());
					advanceTravelRequestEntity.setStatus(0);
					String sequenceId= sequenceID();
					advanceTravelRequestEntity.setSequenceId(sequenceId);
					advanceTravelRequestEntity.setStatusRemarks("Draft");
					advanceTravelRequestEntity = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
				}
				response = MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			}else if(request.getRequestType().equalsIgnoreCase("Miscellaneous")) {
				List<TravelReimbursement> travelReimbursement=request.getTravelReimbursement();
				for (TravelReimbursement travelReimbursement2 : travelReimbursement) {
					advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
					CopyUtility.copyProperties(travelReimbursement2, advanceTravelRequestEntity);
					advanceTravelRequestEntity.setEmployeeId(request.getEmployeeId());
					advanceTravelRequestEntity.setEmployerId(request.getEmployerId());
					advanceTravelRequestEntity.setRequestType(request.getRequestType());
					advanceTravelRequestEntity.setCreatedDate(LocalDateTime.now());
					advanceTravelRequestEntity.setStatus(0);
					String sequenceId= sequenceID();
					advanceTravelRequestEntity.setSequenceId(sequenceId);
					advanceTravelRequestEntity.setStatusRemarks("Draft");
					advanceTravelRequestEntity = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
				}
				response = MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			}
				 
		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			// e.printStackTrace();
			request.setResponse(response);
		}
		return request;
	}

	@Override
	public List<AdvanceTravelDto> getAdvenceTravelListByEmployerId(Long employerid,Long employeeId) {
		List<AdvanceTravelDto> list=new ArrayList<AdvanceTravelDto>();
		try {
			if(employerid!=null && employerid>0) {
				list=advanceTravelRequestDao.findByEmployerId(employerid);			
			}else {
				list=advanceTravelRequestDao.findByEmployeeId(employeeId);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public AdvanceTravelRequestEntity getAdvenceTravelListById(Long id) {
		AdvanceTravelRequestEntity advanceTravelRequestEntity=new AdvanceTravelRequestEntity();
		try {
			advanceTravelRequestEntity=advanceTravelRequestDao.findById(id);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return advanceTravelRequestEntity;
	}
	
	 public Long fetchNextSequenceValue() {
		    return uploadSequenceRepository.getNextSeriesId();
		 }
		
		public String sequenceID() {
			
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
			String str="CDL-";
			String  monthValue=month.length()==1?"0"+month:month;
			String  yearValue=year.substring(2,4);
			
			return str+monthValue+yearValue;
		}

		public String getMessage(Long status) {

			int st = status.intValue();		
			String message = "";		
			switch (st) {
			case 0:
				message = "Draft";
				break;
			case 1:
				message = "Pending";
				break;
			case 2:
				message = "Rejected";
				break;
			case 3:
				message = "Approved";
				break;
			default:
				message = "Draft";
			}
			return message;
		}

		@Override
		public AdvanceTravelAllRequest getAdvenceTravelListByStatus(Long employerid, Long employeeId,
				int status) {
			List<AdvanceTravelRequestEntity> list=new ArrayList<AdvanceTravelRequestEntity>();
			AdvanceTravelAllRequest advanceAllRequest=new AdvanceTravelAllRequest();
			try {
				if(employerid!=null && employerid>0) {
					list=advanceTravelRequestDao.findByEmployerId(employerid,status);			
				}else {
					list=advanceTravelRequestDao.findByEmployeeId(employeeId,status);	
				}
				 
				List<TravelGetReimbursement> travelList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> miscellaneousList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> mealList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> inCityCabList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> accomodationList=new ArrayList<TravelGetReimbursement>();
				if(list!=null) {
					for (AdvanceTravelRequestEntity advanceTravelRequestEntity : list) {
						if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Travel")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							travelList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Meal")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							mealList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Miscellaneous")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							miscellaneousList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("In-City-Cab")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							inCityCabList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Accomodation")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							accomodationList.add(travelReimbursement);
						}
					}
				}
				
				advanceAllRequest.setTravelReimbursement(travelList);
				advanceAllRequest.setMealReimbursement(mealList);
				advanceAllRequest.setMiscellaneousReimbursement(miscellaneousList);
				advanceAllRequest.setInCityCabReimbursement(inCityCabList);
				advanceAllRequest.setAccomodationReimbursement(accomodationList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return advanceAllRequest;
		}

		@Override
		public AdvanceTravelAllUpdateRequest updateAdvenceTravelList(AdvanceTravelAllUpdateRequest advanceTravelAllUpdateRequest) {
			List<AdvanceTravelRequestEntity> list=new ArrayList<AdvanceTravelRequestEntity>();
			AdvanceTravelAllRequest advanceAllRequest=new AdvanceTravelAllRequest();
			try {
								 
				advanceTravelAllUpdateRequest.setResponse(MessageConstant.RESPONSE_FAILED);
				TravelRequestUpdate travelRequestUpdate=advanceTravelAllUpdateRequest.getTravelRequestUpdate();
				List<TravelReimbursement> travelList=travelRequestUpdate.getTravelReimbursement();
				List<TravelReimbursement> miscellaneousList=travelRequestUpdate.getMiscellaneousReimbursement();
				List<TravelReimbursement> mealList=travelRequestUpdate.getMealReimbursement();
				List<TravelReimbursement> inCityCabList=travelRequestUpdate.getInCityCabsReimbursement();
				List<TravelReimbursement> accomodationList=travelRequestUpdate.getAccommodationReimbursement();
				if(travelList!=null) {
					for (TravelReimbursement travelReimbursement : travelList) {
						AdvanceTravelRequestEntity advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
						advanceTravelRequestEntity=advanceTravelRequestDao.findById(travelReimbursement.getId());
						if(advanceTravelRequestEntity!=null) {
							AdvanceTravelRequestEntity advanceTravelRequestEntity1 = new AdvanceTravelRequestEntity();
							//    private String date;
							advanceTravelRequestEntity.setMode(travelReimbursement.getMode());
							advanceTravelRequestEntity.setToBeBookedBy(travelReimbursement.getToBeBookedBy());
							advanceTravelRequestEntity.setDate(travelReimbursement.getDate());//
							advanceTravelRequestEntity.setFromLocation(travelReimbursement.getFromLocation());
							advanceTravelRequestEntity.setToLocation(travelReimbursement.getToLocation());							
							advanceTravelRequestEntity.setPreferredTime(travelReimbursement.getPreferredTime());
							advanceTravelRequestEntity.setRemarks(travelReimbursement.getRemarks());
							Float amount=travelReimbursement.getAmount().floatValue();
							advanceTravelRequestEntity.setAmount(amount);
							advanceTravelRequestEntity.setPaymentMode(travelReimbursement.getPaymentMode());							
							advanceTravelRequestEntity.setCarrierDetails(travelReimbursement.getCarrierDetails());
							advanceTravelRequestEntity.setCarrierClass(travelReimbursement.getCarrierClass());
							advanceTravelRequestEntity.setLimitAmount(travelReimbursement.getLimitAmount());
							advanceTravelRequestEntity.setStatus(1);
							advanceTravelRequestEntity.setStatusRemarks("Submitted");
							advanceTravelRequestEntity1 = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
						}
					}
				}
				if(miscellaneousList!=null) {
					for (TravelReimbursement travelReimbursement : miscellaneousList) {
						AdvanceTravelRequestEntity advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
						advanceTravelRequestEntity=advanceTravelRequestDao.findById(travelReimbursement.getId());
						if(advanceTravelRequestEntity!=null) {
							AdvanceTravelRequestEntity advanceTravelRequestEntity1 = new AdvanceTravelRequestEntity();
				
							advanceTravelRequestEntity.setToBeBookedBy(travelReimbursement.getToBeBookedBy());
							advanceTravelRequestEntity.setDate(travelReimbursement.getDate());
							advanceTravelRequestEntity.setPaymentMode(travelReimbursement.getPaymentMode());
							advanceTravelRequestEntity.setRemarks(travelReimbursement.getRemarks());
							Float amount=travelReimbursement.getAmount().floatValue();
							advanceTravelRequestEntity.setAmount(amount);
							advanceTravelRequestEntity.setLimitAmount(travelReimbursement.getLimitAmount());
							advanceTravelRequestEntity.setTitle(travelReimbursement.getTitle());
						
							advanceTravelRequestEntity.setStatus(1);
							advanceTravelRequestEntity.setStatusRemarks("Submitted");
							advanceTravelRequestEntity1 = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
						}
					}
				}
				if(mealList!=null) {
					for (TravelReimbursement travelReimbursement : mealList) {
						AdvanceTravelRequestEntity advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
						advanceTravelRequestEntity=advanceTravelRequestDao.findById(travelReimbursement.getId());
						if(advanceTravelRequestEntity!=null) {
							AdvanceTravelRequestEntity advanceTravelRequestEntity1 = new AdvanceTravelRequestEntity();							
							
							advanceTravelRequestEntity.setDate(travelReimbursement.getDate());
							advanceTravelRequestEntity.setRemarks(travelReimbursement.getRemarks());
							advanceTravelRequestEntity.setNumberOfDays(travelReimbursement.getNumberOfDays());
							advanceTravelRequestEntity.setTypeOfMeal(travelReimbursement.getTypeOfMeal());
							advanceTravelRequestEntity.setLocation(travelReimbursement.getLocation());
							advanceTravelRequestEntity.setPaymentMode(travelReimbursement.getPaymentMode());
							Float amount=travelReimbursement.getAmount().floatValue();
							advanceTravelRequestEntity.setAmount(amount);
							advanceTravelRequestEntity.setLimitAmount(travelReimbursement.getLimitAmount());							
							advanceTravelRequestEntity.setStatus(1);
							advanceTravelRequestEntity.setStatusRemarks("Submitted");
							advanceTravelRequestEntity1 = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
						}
					}
				}
				if(inCityCabList!=null) {
					for (TravelReimbursement travelReimbursement : inCityCabList) {
						AdvanceTravelRequestEntity advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
						advanceTravelRequestEntity=advanceTravelRequestDao.findById(travelReimbursement.getId());
						if(advanceTravelRequestEntity!=null) {
							AdvanceTravelRequestEntity advanceTravelRequestEntity1 = new AdvanceTravelRequestEntity();

							advanceTravelRequestEntity.setToBeBookedBy(travelReimbursement.getToBeBookedBy());
							advanceTravelRequestEntity.setMode(travelReimbursement.getMode());
							advanceTravelRequestEntity.setDate(travelReimbursement.getDate());							
							advanceTravelRequestEntity.setPaymentMode(travelReimbursement.getPaymentMode());
							advanceTravelRequestEntity.setRemarks(travelReimbursement.getRemarks());
							advanceTravelRequestEntity.setPreferredTime(travelReimbursement.getPreferredTime());
							advanceTravelRequestEntity.setFromLocation(travelReimbursement.getFromLocation());
							advanceTravelRequestEntity.setToLocation(travelReimbursement.getToLocation());
							Float amount=travelReimbursement.getAmount().floatValue();
							advanceTravelRequestEntity.setAmount(amount);
							advanceTravelRequestEntity.setLimitAmount(travelReimbursement.getLimitAmount());
							advanceTravelRequestEntity.setStatus(1);
							advanceTravelRequestEntity.setStatusRemarks("Submitted");
							advanceTravelRequestEntity1 = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
						}
					}
				}
				if(accomodationList!=null) {
					for (TravelReimbursement travelReimbursement : accomodationList) {
						AdvanceTravelRequestEntity advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
						advanceTravelRequestEntity=advanceTravelRequestDao.findById(travelReimbursement.getId());
						if(advanceTravelRequestEntity!=null) {
							AdvanceTravelRequestEntity advanceTravelRequestEntity1 = new AdvanceTravelRequestEntity();
				
							advanceTravelRequestEntity.setToBeBookedBy(travelReimbursement.getToBeBookedBy());							
							advanceTravelRequestEntity.setRemarks(travelReimbursement.getRemarks());							
							advanceTravelRequestEntity.setHotelDetails(travelReimbursement.getHotelDetails());							
							advanceTravelRequestEntity.setCheckinDate(travelReimbursement.getCheckinDate());
							advanceTravelRequestEntity.setCheckoutDate(travelReimbursement.getCheckoutDate());							
							advanceTravelRequestEntity.setType(travelReimbursement.getType());
							advanceTravelRequestEntity.setLocation(travelReimbursement.getLocation());
							advanceTravelRequestEntity.setPreferredTime(travelReimbursement.getPreferredTime());
							advanceTravelRequestEntity.setPaymentMode(travelReimbursement.getPaymentMode());
							Float amount=travelReimbursement.getAmount().floatValue();
							advanceTravelRequestEntity.setAmount(amount);
							advanceTravelRequestEntity.setLimitAmount(travelReimbursement.getLimitAmount());
							advanceTravelRequestEntity.setStatus(1);
							advanceTravelRequestEntity.setStatusRemarks("Submitted");
							advanceTravelRequestEntity1 = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
						}
					}
				}
				
				
				advanceTravelAllUpdateRequest.setResponse(MessageConstant.RESPONSE_SUCCESS);
//				advanceAllRequest.setTravelReimbursement(travelList);
//				advanceAllRequest.setMealReimbursement(mealList);
//				advanceAllRequest.setMiscellaneousReimbursement(miscellaneousList);
//				advanceAllRequest.setInCityCabReimbursement(inCityCabList);
//				advanceAllRequest.setAccomodationReimbursement(accomodationList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return advanceTravelAllUpdateRequest;
		}

		@Override
		public String deleteAdvenceTravelById(Long id) {
			// TODO Auto-generated method stub
			advanceTravelRequestDao.deleteById(id);
			return MessageConstant.RESPONSE_SUCCESS;
		}

		@Override
		public List<AdvanceTravelDto> getAdvenceTravelApprovalEmployerId(Long employerid) {
			
			List<AdvanceTravelDto> list=new ArrayList<AdvanceTravelDto>();
			
			List<AdvanceTravelDto> listUpdate=new ArrayList<AdvanceTravelDto>();
			
			try {
				
				if(employerid!=null && employerid>0) {
					list=advanceTravelRequestDao.findApprovalByEmployerId(employerid);
					for (AdvanceTravelDto advanceTravelRequestEntity : list) {
						if(advanceTravelRequestEntity.getApprovedStatus()==1) {
							advanceTravelRequestEntity.setStatusRemarks("Approved");
						}else if(advanceTravelRequestEntity.getApprovedStatus()==2) {
							advanceTravelRequestEntity.setStatusRemarks("Rejected");
						}else {
							advanceTravelRequestEntity.setStatusRemarks("Pending");
						}
						listUpdate.add(advanceTravelRequestEntity);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listUpdate;
		}

		@Override
		public ApprovalTravelReimbursement ApprovalAdvenceTravel(ApprovalTravelReimbursement approvalTravelReimbursement) {
			try {
				approvalTravelReimbursement.setResponse(MessageConstant.RESPONSE_FAILED);
				AdvanceTravelRequestEntity advanceTravelRequestEntity = new AdvanceTravelRequestEntity();
				advanceTravelRequestEntity=advanceTravelRequestDao.findById(approvalTravelReimbursement.getId());
				if(advanceTravelRequestEntity!=null && advanceTravelRequestEntity.getApprovedStatus()==0) {
					advanceTravelRequestEntity.setApprovedStatusRemarks(approvalTravelReimbursement.getApprovalRemarks());
					advanceTravelRequestEntity.setApprovedBy(approvalTravelReimbursement.getApprovedBy());
					advanceTravelRequestEntity.setApprovedDate(LocalDate.now());
					Float approvedAmount=approvalTravelReimbursement.getApprovalAmount()==null?0:approvalTravelReimbursement.getApprovalAmount().floatValue();
					advanceTravelRequestEntity.setApprovedAmount(approvedAmount);
					String approvedOrRejected=approvalTravelReimbursement.getApprovedOrRejected();
					advanceTravelRequestEntity.setStatusRemarks(approvedOrRejected);
					if(approvedOrRejected.equalsIgnoreCase(MessageConstant.APPROVED)) {
						advanceTravelRequestEntity.setApprovedStatus(1);
					}else {
						advanceTravelRequestEntity.setApprovedStatus(2);
					}
					advanceTravelRequestEntity = advanceTravelRequestDao.saveDetails(advanceTravelRequestEntity);
				}
				approvalTravelReimbursement.setResponse(MessageConstant.RESPONSE_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return approvalTravelReimbursement;
		}

		@Override
		public AdvanceTravelAllRequest getAdvenceTravelBySequenceIdListByStatus(Long employerid, Long employeeId,
				int status, String sequenceId) {
			List<AdvanceTravelRequestEntity> list=new ArrayList<AdvanceTravelRequestEntity>();
			AdvanceTravelAllRequest advanceAllRequest=new AdvanceTravelAllRequest();
			try {
				if(employerid!=null && employerid>0) {
					list=advanceTravelRequestDao.findByEmployerSequenceId(employerid,sequenceId);			
				}else {
					list=advanceTravelRequestDao.findByEmployeeSequenceId(employeeId,sequenceId);	
				}
				 
				List<TravelGetReimbursement> travelList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> miscellaneousList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> mealList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> inCityCabList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> accomodationList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> cash=new ArrayList<TravelGetReimbursement>();
				if(list!=null) {
					for (AdvanceTravelRequestEntity advanceTravelRequestEntity : list) {
						if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Travel")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							travelList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Meal")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							mealList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Miscellaneous")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							miscellaneousList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("In-City-Cab")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							inCityCabList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Accomodation")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							accomodationList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Cash")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							cash.add(travelReimbursement);
						}
					}
				}
				
				advanceAllRequest.setTravelReimbursement(travelList);
				advanceAllRequest.setMealReimbursement(mealList);
				advanceAllRequest.setMiscellaneousReimbursement(miscellaneousList);
				advanceAllRequest.setInCityCabReimbursement(inCityCabList);
				advanceAllRequest.setAccomodationReimbursement(accomodationList);
				advanceAllRequest.setCash(cash);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return advanceAllRequest;
		}

		@Override
		public AdvanceTravelAllRequest getAdvenceTravelListByDraftStatus(Long employerid, Long employeeId, int status) {
			List<AdvanceTravelRequestEntity> list=new ArrayList<AdvanceTravelRequestEntity>();
			AdvanceTravelAllRequest advanceAllRequest=new AdvanceTravelAllRequest();
			try {
				if(employerid!=null && employerid>0) {
					list=advanceTravelRequestDao.findByEmployerIdDraft(employerid);			
				}else {
					list=advanceTravelRequestDao.findByEmployeeIdDraft(employeeId);	
				}
				 
				List<TravelGetReimbursement> travelList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> miscellaneousList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> mealList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> inCityCabList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> accomodationList=new ArrayList<TravelGetReimbursement>();
				List<TravelGetReimbursement> cash=new ArrayList<TravelGetReimbursement>();
				if(list!=null) {
					for (AdvanceTravelRequestEntity advanceTravelRequestEntity : list) {
						if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Travel")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							travelList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Meal")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							mealList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Miscellaneous")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							miscellaneousList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("In-City-Cab")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							inCityCabList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Accomodation")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							accomodationList.add(travelReimbursement);
						}else if(advanceTravelRequestEntity.getRequestType().equalsIgnoreCase("Cash")) {
							TravelGetReimbursement travelReimbursement=new TravelGetReimbursement();
							CopyUtility.copyProperties(advanceTravelRequestEntity, travelReimbursement);
							advanceAllRequest.setEmployerId(advanceTravelRequestEntity.getEmployerId());
							System.out.println(advanceTravelRequestEntity.getCreatedDate());
							DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
							LocalDateTime localDateTime=advanceTravelRequestEntity.getCreatedDate();
							String formattedDate = localDateTime.format(dateFormatter);
							System.out.println(formattedDate);
							String formattedTime = localDateTime.format(timeFormatter);
							System.out.println(formattedTime);
							travelReimbursement.setCreatedDate(formattedDate);
							travelReimbursement.setCreatedTime(formattedTime);
							cash.add(travelReimbursement);
						}
					}
				}
				
				advanceAllRequest.setTravelReimbursement(travelList);
				advanceAllRequest.setMealReimbursement(mealList);
				advanceAllRequest.setMiscellaneousReimbursement(miscellaneousList);
				advanceAllRequest.setInCityCabReimbursement(inCityCabList);
				advanceAllRequest.setAccomodationReimbursement(accomodationList);
				advanceAllRequest.setCash(cash);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return advanceAllRequest;
		}


		
}

