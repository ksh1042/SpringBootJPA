package com.roman14.springbootjpa.repository;

import com.roman14.springbootjpa.entity.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository
{
  private final EntityManager em;

  public void save(Item item)
  {
    if(item.getId() == null)
    {
      em.persist(item);
    }
    else
    {
      // merge는 파라미터로 받은 객체를 영속성 컨텍스트에 등록시키지 않는다. return 된 객체가 영속성 컨텍스트에 등록된 상태.
      em.merge(item);
    }
  }

  public Item findOne(Long id)
  {
    return em.find(Item.class, id);
  }

  public List<Item> findAll()
  {
    return em.createQuery("select i from Item i", Item.class)
      .getResultList();
  }
}
