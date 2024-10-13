package spring.data.jpa.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import spring.data.jpa.entity.Member;

@Repository
public class MemberJpaRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Member save(Member member) {
		em.persist(member);
		return member;
	}
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
	
}
