package com.roman14.springbootjpa.repository;

import com.roman14.springbootjpa.entity.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SuppressWarnings("RedundantThrows")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest
{
  @Autowired
  private MemberRepository repository;

  @BeforeEach
  void setUp()
  {
  }

  @AfterEach
  void tearDown()
  {
  }

  @Test
  @Transactional
  @Rollback(false)
  public void save() throws Exception
  {
    // given
    final Member member = new Member();
    member.setName("name01");
    member.setAddTime(LocalDateTime.now());

    // when
    Long memberId = repository.save(member);
    Member findMember = repository.findOne(memberId);

    // then
    Assertions.assertEquals(
      member.getId(), findMember.getId()
    );
  }
}