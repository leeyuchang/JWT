package com.home.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import java.time.LocalDateTime;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Link {

  @Id
  private String id;
  private String title;
  private String url;
  @JsonFormat(shape = Shape.STRING)
  @DateTimeFormat(pattern = "yyyyMMddHH")
  private LocalDateTime createDate;

}
