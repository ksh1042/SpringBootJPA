package com.roman14.springbootjpa.api.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponse<T>
{
  private HttpStatus status;
  private T data;
  private String message;

  @Builder
  public CommonResponse(HttpStatus status, T data, String message)
  {
    this.status = status;
    this.data = data;
    this.message = message;
  }

  public int getStatus()
  {
    return this.status.value();
  }

  public static <T> CommonResponse<T> success(T data)
  {
    return CommonResponse.<T>builder()
      .status(HttpStatus.OK)
      .data(data)
      .build();
  }

  public static <T> CommonResponse<T> error(Throwable exception)
  {
    return CommonResponse.<T>builder()
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .data(null)
      .message(exception.getMessage())
      .build();
  }
}
