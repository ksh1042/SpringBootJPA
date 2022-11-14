package com.roman14.springbootjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Entity
@Getter @Setter
public class Member
{
  @Id @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();

  private LocalDateTime addTime;

  @Embedded
  private Address address;

  @Lob
  private String description;

}
