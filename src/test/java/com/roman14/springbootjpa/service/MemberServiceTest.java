package com.roman14.springbootjpa.service;

import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

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

  @Autowired
  private EntityManager em;


  @BeforeEach
  void setUp()
  {
  }

  @Test
  @Transactional
  @DisplayName("회원가입 테스트")
  @Rollback(value = false)
  public void signUpTest() throws Exception
  {
    // given
    final Member member = new Member();
    member.setName("name01");
    member.setAddTime(LocalDateTime.now());

    // when
    Long signUpId = service.signUp(member);

    em.flush();

    // then
    Assertions.assertEquals(member, repository.findOne(signUpId));
  }

  @Test
  @DisplayName("회원가입 테스트 - 회원 중복 체크")
  public void signUpTest2() throws Exception
  {
    // given
    Member member1 = new Member();
    member1.setName("name01");

    Member member2 = new Member();
    member2.setName("name02");

    // when
    Assertions.assertThrows(IllegalStateException.class, ()->{
      service.signUp(member1);
      service.signUp(member2);
    });

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