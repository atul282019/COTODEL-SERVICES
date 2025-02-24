package com.cotodel.hrms.auth.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
  private String access_token;
  private String type = "Bearer";
  private String txnId;
  private String username;
  private String email;
  private String timestamp;
 
 
}
