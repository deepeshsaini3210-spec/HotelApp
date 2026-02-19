/*
* Copyright © 2023–2025 HumainOS. All rights reserved.
*
* This source code is proprietary and confidential information of HumainOS.
* Unauthorized copying, modification, distribution, or use in whole or in part
* is strictly prohibited without the express written permission of HumainOS.
*/

package com.grandstay.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * A custom annotation that provides standardized API response documentation using Swagger/OpenAPI.
 * This annotation combines multiple common API response codes into a single reusable annotation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
  @ApiResponse(responseCode = "200", description = "Success|OK"),
  @ApiResponse(responseCode = "401", description = "Not authorized"),
  @ApiResponse(responseCode = "403", description = "Forbidden"),
  @ApiResponse(responseCode = "404", description = "Not found"),
  @ApiResponse(responseCode = "500", description = "Invalid parameters")
})
public @interface SFApiResponses {
  // No additional elements needed
}
