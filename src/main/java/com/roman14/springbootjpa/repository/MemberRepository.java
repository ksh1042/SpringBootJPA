package com.roman14.springbootjpa.repository;

import com.roman14.springbootjpa.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository
{
  /*
  @PersistenceUnit
  private EntityManagerFactory emf;
  */

  // 엔티티 매니저
  /**
   * @PersistenceContext 스프링 부트에서 생성된 앤티티매니저 의존성 주입
   */
  @SuppressWarnings("JavaDoc")
  @PersistenceContext
  private EntityManager em;

  public Long save(Member member)
  {
    em.persist(member);

    return member.getId();  // CQS 원칙으로, 사이드 이펙트 영향을 줄이기 위해 객체 자체를 반환하는 것을 권장하지 않는다.
  }

  public Member findOne(Long id)
  {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em.createQuery("SELECT m FROM Member m", Member.class)
      .getResultList();
  }

  public List<Member> findByName(String name)
  {
    return em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
      .setParameter("name", name)
      .getResultList();
  }

}
