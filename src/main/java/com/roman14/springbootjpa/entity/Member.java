package com.roman14.springbootjpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Member
{
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private LocalDateTime addTime;
}
