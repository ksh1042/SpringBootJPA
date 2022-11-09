package com.roman14.springbootjpa.api;

import com.roman14.springbootjpa.entity.Address;
import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.service.MemberService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Embedded;
import javax.persistence.Lob;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController
{
  private final MemberService memberService;

  @Deprecated
  @PostMapping("/v1/new")
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member)
  {
    Long memberId = memberService.signUp(member);

    return new CreateMemberResponse(memberId);
  }

  @PostMapping("/v2/new")
  public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request)
  {
    Member member = new Member();
    member.setName(request.getName());
    member.setAddress(request.getAddress());

    Long memberId = memberService.signUp(member);

    return new CreateMemberResponse(memberId);
  }

  @PutMapping("/v1/edit/{memberId}")
  public EditMemberResponse editMemberV1(@PathVariable Long memberId, @RequestBody @Valid EditMemberRequest request)
  {
    memberService.update(memberId, request.getName());

    Member member = memberService.findMember(memberId);

    return EditMemberResponse.of(member);
  }

  @GetMapping("/v1/list")
  public List<Member> retrieveMembersV1()
  {
    return memberService.findMembers();
  }

  @GetMapping("/v2/list")
  public CommonApiResponse<?> retrieveMembersV2()
  {
    List<Member> members = memberService.findMembers();

    List<MemberDto> result = members.stream()
      .map(MemberDto::of)
      .collect(Collectors.toList());

    return CommonApiResponse.success(result);
  }

  // ===================================================================================================================

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

  @Data
  static class EditMemberResponse
  {
    private Long id;
    private String name;
    private Address address;
    private String description;
    private LocalDateTime addTime;

    public static EditMemberResponse of(Member member)
    {
      EditMemberResponse response = new EditMemberResponse();
      response.setId(member.getId());
      response.setName(member.getName());
      response.setAddress(member.getAddress());
      response.setDescription(member.getDescription());
      response.setAddTime(member.getAddTime());

      return response;
    }
  }

  @Data
  static class EditMemberRequest
  {
    @NotEmpty
    @Size(min = 3, max = 15)
    private String name;
  }

  @Data
  static class CommonApiResponse<T>
  {
    private HttpStatus status;
    private T data;

    @Deprecated
    private CommonApiResponse()
    {
    }

    @Builder
    public CommonApiResponse(T data, HttpStatus status)
    {
      this.status = status;
      this.data = data;
    }

    public static <T> CommonApiResponse<T> success(T data)
    {
      CommonApiResponse<T> response = new CommonApiResponse<>();
      response.setStatus(HttpStatus.OK);
      response.setData(data);

      return response;
    }

    public int getStatus()
    {
      return this.status.value();
    }
  }

  @Data
  static class MemberDto
  {
    private Long id;
    private String name;
    private LocalDateTime addTime;
    private Address address;
    private String description;

    public static MemberDto of(Member member)
    {
      MemberDto dto = new MemberDto();
      dto.setId(member.getId());
      dto.setName(member.getName());
      dto.setAddTime(member.getAddTime());
      dto.setAddress(member.getAddress());
      dto.setDescription(member.getDescription());

      return dto;
    }
  }
}

