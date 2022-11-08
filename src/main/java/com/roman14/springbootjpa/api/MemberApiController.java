package com.roman14.springbootjpa.api;

import com.roman14.springbootjpa.entity.Address;
import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController
{
  private final MemberService memberService;

  @Deprecated
  @RequestMapping(value = "/v1/new", method = RequestMethod.POST)
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member)
  {
    Long memberId = memberService.signUp(member);

    return new CreateMemberResponse(memberId);
  }

  @RequestMapping(value = "/v2/new", method = RequestMethod.POST)
  public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request)
  {
    Member member = new Member();
    member.setName(request.getName());
    member.setAddress(request.getAddress());

    Long memberId = memberService.signUp(member);

    return new CreateMemberResponse(memberId);
  }

  @Data
  static class CreateMemberResponse {
    private Long id;

    public CreateMemberResponse(Long id)
    {
      this.id = id;
    }
  }

  @Data
  static class CreateMemberRequest {
    @NotEmpty
    @Size(min = 3, max = 15)
    private String name;
    @NotNull
    private Address address;
  }
}
