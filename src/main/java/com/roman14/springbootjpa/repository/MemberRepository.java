package com.roman14.springbootjpa.repository;

import com.roman14.springbootjpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository
{
  /**
   * @PersistenceContext 스프링 부트에서 생성한 앤티티매니저 의존성 주입
   */
  @PersistenceContext
  private EntityManager em;

  public Long save(Member member)
  {
    em.persist(member);

    return member.getId();  // CQS 원칙으로, 사이드 이펙트 영향을 줄이기 위해 객체 자체를 반환하는 것을 권장하지 않는다.
  }

  public Member find(Long id)
  {
    return em.find(Member.class, id);
  }

}
