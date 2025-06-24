	package com.cotodel.hrms.auth.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.CashFreeOrderHistory;
import com.cotodel.hrms.auth.server.dto.CashFreeOrderIdResponse;
import com.cotodel.hrms.auth.server.dto.CashFreeOrderListHistoryResponse;
import com.cotodel.hrms.auth.server.dto.CashFreeOrderListResponse;
import com.cotodel.hrms.auth.server.dto.CashFreeOrderResponse;
import com.cotodel.hrms.auth.server.dto.CashFreeOrderUpdateResponse;
import com.cotodel.hrms.auth.server.dto.CurrentMonthAmountLimitResponse;
import com.cotodel.hrms.auth.server.dto.CurrentMonthLimitResponse;
import com.cotodel.hrms.auth.server.dto.DataUpdateRequest;
import com.cotodel.hrms.auth.server.dto.OrderIdResponse;
import com.cotodel.hrms.auth.server.dto.OrderUserRequest;
import com.cotodel.hrms.auth.server.dto.OrderUserUpdateRequest;
import com.cotodel.hrms.auth.server.entity.CashFreeOrderWebHookEntity;
import com.cotodel.hrms.auth.server.exception.ApiError;
import com.cotodel.hrms.auth.server.service.CashService;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.TransactionManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@RestController
@RequestMapping("/Api")
public class CashFreeController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(CashFreeController.class);
	
	@Autowired
	CashService cashService;
	
	 @Operation(summary = "This API will provide the Save User Details ", security = {
				@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
				@ApiResponses(value = {
				@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
				@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
				@RequestMapping(value = "/get/cashFreeOrder",produces = {"application/json"},consumes = {"application/json","application/text"},method = RequestMethod.POST)
					    public ResponseEntity<Object> voucherCreation(@RequestBody OrderUserRequest orderUserRequest) {
					
		 			OrderUserRequest response=null;
					try {						
					    logger.info("inside /get/cashFreeOrder::."+orderUserRequest);
					    
					    response= cashService.callOrderApi(orderUserRequest);
					    
					    if(response!=null && response.getPayment_session_id()!=null) {
					    	return ResponseEntity.ok(new CashFreeOrderResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getCurrentTimeStamp()));
					    }else {
					    	return ResponseEntity.ok(new CashFreeOrderResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					    }
							 
					} catch (Exception e) {
						e.printStackTrace();
						
						return ResponseEntity.ok(new CashFreeOrderResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					}
					
				}
	 			
	 			@Operation(summary = "This API will provide the Save User Details ", security = {
				@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
				@ApiResponses(value = {
				@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
				@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
				@RequestMapping(value = "/get/cashFreeOrderId",produces = {"application/json"},consumes = {"application/json","application/text"},method = RequestMethod.POST)
					    public ResponseEntity<Object> cashFreeOrderId(@RequestBody OrderUserRequest orderUserRequest) {
					
	 				OrderIdResponse response=null;
					try {						
					    logger.info("inside /get/cashFreeOrderId...."+orderUserRequest);
					    
					    response= cashService.callOrderIdApi(orderUserRequest);
					    
					    if(response!=null ) {
					    	return ResponseEntity.ok(new CashFreeOrderIdResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getCurrentTimeStamp()));
					    }else {
					    	return ResponseEntity.ok(new CashFreeOrderIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					    }
							 
					} catch (Exception e) {
						e.printStackTrace();
						
						return ResponseEntity.ok(new CashFreeOrderIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					}
					
				}
	 
	 			@Operation(summary = "This API will provide the Save User Details ", security = {
				@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
				@ApiResponses(value = {
				@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
				@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
				@RequestMapping(value = "/get/cashFreeOrderIdView",produces = {"application/json"},consumes = {"application/json","application/text"},method = RequestMethod.POST)
					    public ResponseEntity<Object> cashFreeOrderIdView(@RequestBody OrderUserRequest orderUserRequest) {
					
	 				OrderIdResponse response=null;
					try {						
					    logger.info("inside /get/cashFreeOrderIdView::."+orderUserRequest);
					    
					    response= cashService.callOrderIdApiView(orderUserRequest);
					    
					    if(response!=null ) {
					    	return ResponseEntity.ok(new CashFreeOrderIdResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getCurrentTimeStamp()));
					    }else {
					    	return ResponseEntity.ok(new CashFreeOrderIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					    }
							 
					} catch (Exception e) {
						e.printStackTrace();
						
						return ResponseEntity.ok(new CashFreeOrderIdResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					}
					
				}
	 
	 			@Operation(summary = "This API will provide the Save User Details ", security = {
				@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
				@ApiResponses(value = {
				@ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
				@ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
				@ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
				@RequestMapping(value = "/get/cashFreeOrderIdUpdate",produces = {"application/json"},consumes = {"application/json","application/text"},method = RequestMethod.POST)
					    public ResponseEntity<Object> cashFreeOrderIdUpdate(@RequestBody OrderUserUpdateRequest orderUserUpdateRequest) {
					
		 		OrderUserUpdateRequest response=null;
					try {						
					    logger.info("inside get/cashFreeOrderIdUpdate::"+orderUserUpdateRequest);
					    
					    response= cashService.callOrderIdApiUpdate(orderUserUpdateRequest);
					    
					    if(response!=null && response.getResponse().equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
					    	return ResponseEntity.ok(new CashFreeOrderUpdateResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,response,TransactionManager.getCurrentTimeStamp()));
					    }else {
					    	return ResponseEntity.ok(new CashFreeOrderUpdateResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					    }
							 
					} catch (Exception e) {
						e.printStackTrace();
						
						return ResponseEntity.ok(new CashFreeOrderUpdateResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,response,TransactionManager.getCurrentTimeStamp()));
					}
					
				}
	 
	 			@Operation(summary = "This API will provide the Save User Details ", security = {
	 					@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	 					@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
						@ApiResponse(responseCode = "400", description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
						@ApiResponse(responseCode = "404", description = "Request Resource was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
						@ApiResponse(responseCode = "500", description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
				@RequestMapping(value = "/get/cashFreeOrderIdList", produces = { "application/json" }, consumes = {
						"application/json", "application/text" }, method = RequestMethod.POST)
				public ResponseEntity<Object> cashFreeOrderIdList(@RequestBody OrderUserRequest orderUserRequest) {

					List<CashFreeOrderHistory> response = null;
					try {

						logger.info("inside ..cashFreeOrderIdList.::" + orderUserRequest);

						response = cashService.callOrderIdApiList(orderUserRequest);

						if (response != null && response.size()>0) {
							return ResponseEntity.ok(new CashFreeOrderListHistoryResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND, response,
									TransactionManager.getCurrentTimeStamp()));
						} else {
							return ResponseEntity.ok(new CashFreeOrderListHistoryResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND, response,
									TransactionManager.getCurrentTimeStamp()));
						}

					} catch (Exception e) {
						e.printStackTrace();

						return ResponseEntity.ok(new CashFreeOrderListHistoryResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND, response, TransactionManager.getCurrentTimeStamp()));
					}

				}
	 			@Operation(summary = "This API will provide the Save User Details ", security = {
	 					@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	 					@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
						@ApiResponse(responseCode = "400", description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
						@ApiResponse(responseCode = "404", description = "Request Resource was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
						@ApiResponse(responseCode = "500", description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
				@RequestMapping(value = "/get/cashFreeAmountLimit", produces = { "application/json" }, consumes = {
						"application/json", "application/text" }, method = RequestMethod.POST)
				public ResponseEntity<Object> cashFreeAmountLimit(@RequestBody OrderUserRequest orderUserRequest) {

					List<CashFreeOrderHistory> response = null;
					try {

						logger.info("inside ..cashFreeOrderIdList.::" + orderUserRequest);

						response = cashService.callOrderIdApiList(orderUserRequest);

						if (response != null && response.size()>0) {
							return ResponseEntity.ok(new CashFreeOrderListHistoryResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND, response,
									TransactionManager.getCurrentTimeStamp()));
						} else {
							return ResponseEntity.ok(new CashFreeOrderListHistoryResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND, response,
									TransactionManager.getCurrentTimeStamp()));
						}

					} catch (Exception e) {
						e.printStackTrace();

						return ResponseEntity.ok(new CashFreeOrderListHistoryResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND, response, TransactionManager.getCurrentTimeStamp()));
					}

				}
	 			
	 			@Operation(summary = "This API will provide the Save User Details ", security = {
	 					@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
	 					@ApiResponses(value = {
						@ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
						@ApiResponse(responseCode = "400", description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
						@ApiResponse(responseCode = "404", description = "Request Resource was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
						@ApiResponse(responseCode = "500", description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
				@RequestMapping(value = "/get/currentMonthAmountLimit", produces = { "application/json" }, consumes = {
						"application/json", "application/text" }, method = RequestMethod.POST)
				public ResponseEntity<Object> currentMonthAmountLimit(@RequestBody OrderUserRequest orderUserRequest) {

	 				CurrentMonthLimitResponse response = null;
					try {

						logger.info("inside ..currentMonthAmountLimit.::" + orderUserRequest);

						response = cashService.cashFreeCurrentMonthAmount(orderUserRequest);

						if (response != null) {
							return ResponseEntity.ok(new CurrentMonthAmountLimitResponse(MessageConstant.TRUE,MessageConstant.DATA_FOUND, response,
									TransactionManager.getCurrentTimeStamp()));
						} else {
							return ResponseEntity.ok(new CurrentMonthAmountLimitResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND, response,
									TransactionManager.getCurrentTimeStamp()));
						}

					} catch (Exception e) {
						e.printStackTrace();

						return ResponseEntity.ok(new CurrentMonthAmountLimitResponse(MessageConstant.FALSE,MessageConstant.DATA_NOT_FOUND, response, TransactionManager.getCurrentTimeStamp()));
					}

				}
}
