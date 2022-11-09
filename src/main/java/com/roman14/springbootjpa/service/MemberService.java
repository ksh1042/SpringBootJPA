package com.roman14.springbootjpa.service;

import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("JavaDoc")
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor  // Lombok을 통한 의존성 주입
public class MemberService
{
  private final MemberRepository memberRepository;

  // 회원가입
  @Transactional
  public Long signUp(Member member)
  {
    validateDuplicatedMember(member);
    member.setAddTime(LocalDateTime.now());
    memberRepository.save(member);

    return member.getId();
  }

  /**
   * 중복회원 검증
   * @param member
   */
  private void validateDuplicatedMember(Member member)
  {
    List<Member> findMembers = memberRepository.findByName(member.getName());
    if( !findMembers.isEmpty() ) throw new IllegalStateException("member is exist");
  }

  // 회원전체 조회
  public List<Member> findMembers()
  {
    return memberRepository.findAll();
  }

  public Member findMember(Long id)
  {
    return memberRepository.findOne(id);
  }

  @Transactional
  public Long update(Long memberId, String name)
  {
    Member member = memberRepository.findOne(memberId);
    member.setName(name);

    return member.getId();
  }
}
