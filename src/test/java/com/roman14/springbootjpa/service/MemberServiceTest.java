package com.roman14.springbootjpa.service;

import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("RedundantThrows")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional(readOnly = true)
class MemberServiceTest
{
  @Autowired
  private MemberService service;

  @Autowired
  private MemberRepository repository;


  @BeforeEach
  void setUp()
  {
  }

  @Test
  @Transactional
//  @Rollback(value = false)
  public void signUpTest() throws Exception
  {
    // given
    Member member = Member.builder()
      .name("member01")
      .build();

    // when
    Long signUpId = service.signUp(member);

    // then
    Assertions.assertEquals(member, repository.findOne(signUpId));
  }

  @Test
  public void findMembersTest() throws Exception
  {
    // given

    // when

    // then
  }

  @Test
  public void findMemberTest() throws Exception
  {
    // given

    // when

    // then
  }

  @AfterEach
  void tearDown()
  {
  }
}