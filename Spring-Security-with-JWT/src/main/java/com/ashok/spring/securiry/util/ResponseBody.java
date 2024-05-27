package com.ashok.spring.securiry.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseBody {

	String jwt_token;
}
