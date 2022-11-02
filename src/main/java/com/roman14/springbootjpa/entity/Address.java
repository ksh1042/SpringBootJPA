package com.roman14.springbootjpa.entity;

import com.roman14.springbootjpa.controller.MemberForm;
import lombok.Getter;

import javax.persistence.Embeddable;


@Embeddable
@Getter
public class Address
{
  private String city;
  private String street;
  private String zipCode;

  protected Address() {}

  public Address(String city, String street, String zipCode)
  {
    this.city = city;
    this.street = street;
    this.zipCode = zipCode;
  }

  public static Address of(MemberForm memberForm)
  {
    return new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
  }
}
