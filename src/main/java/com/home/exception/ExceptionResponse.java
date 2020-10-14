package com.home.exception;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

  @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime timestamp;
  private String message;
  private String details;

}
