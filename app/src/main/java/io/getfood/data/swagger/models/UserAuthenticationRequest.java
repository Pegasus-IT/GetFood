/*
 * Api documentation
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.getfood.data.swagger.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * UserAuthenticationRequest
 */

public class UserAuthenticationRequest {

  @SerializedName("email")
  private String email = null;

  @SerializedName("password")
  private String password = null;
  public UserAuthenticationRequest email(String email) {
    this.email = email;
    return this;
  }

  

  /**
  * Get email
  * @return email
  **/
  @Schema(example = "john.doe@example.com", required = true, description = "")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public UserAuthenticationRequest password(String password) {
    this.password = password;
    return this;
  }

  

  /**
  * Get password
  * @return password
  **/
  @Schema(example = "Test123!", required = true, description = "")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserAuthenticationRequest userAuthenticationRequest = (UserAuthenticationRequest) o;
    return Objects.equals(this.email, userAuthenticationRequest.email) &&
        Objects.equals(this.password, userAuthenticationRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserAuthenticationRequest {\n");

    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
