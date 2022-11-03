package com.roman14.springbootjpa.controller;

import com.roman14.springbootjpa.entity.Address;
import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController
{
  private final MemberService memberService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String listMember(Model model)
  {
    List<Member> memberList = memberService.findMembers();

    model.addAttribute("members", memberList);

    return "members/memberList";
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String createForm(Model model)
  {
    model.addAttribute("memberForm", new MemberForm());

    return "members/createMemberForm";
  }
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String createMember(@Valid MemberForm memberForm, BindingResult result)
  {
    if(result.hasErrors()) return "members/createMemberForm";

    Address address = Address.of(memberForm);

    Member member = new Member();
    member.setName(memberForm.getName());
    member.setAddress(address);

    memberService.signUp(member);

    return "redirect:/";
  }



}
