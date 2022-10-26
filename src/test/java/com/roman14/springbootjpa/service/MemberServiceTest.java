package com.roman14.springbootjpa.service;

import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SuppressWarnings("RedundantThrows")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest
{
  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepository repository;

//  @Autowired
//  EntityManager em;


  @BeforeEach
  void setUp()
  {
  }

  @Test
  @DisplayName("회원가입 테스트")
  void signUpTest() throws Exception
  {
    // given
    final Member member = new Member();
    member.setName("name01");
    member.setAddTime(LocalDateTime.now());

    // when
    Long signUpId = memberService.signUp(member);

    // then
    Assertions.assertEquals(member, repository.findOne(signUpId));
  }

  @Test
  @DisplayName("회원가입 테스트 - 회원 중복 체크")
  void signUpTest2() throws Exception
  {
    // given
    Member member1 = new Member();
    member1.setName("kim");

    Member member2 = new Member();
    member2.setName("kim");

    // when
    memberService.signUp(member1);

    // then
    Assertions.assertThrows(IllegalStateException.class, ()-> memberService.signUp(member2));
  }

  @AfterEach
  void tearDown()
  {
  }
}