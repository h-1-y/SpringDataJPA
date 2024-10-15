package spring.data.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import spring.data.jpa.entity.Member;

@Repository
public class MemberJpaRepository {

	@PersistenceContext
	private EntityManager em;
	
	// 저장 
	public Member save(Member member) {
		em.persist(member);
		return member;
	}
	
	// 삭제 
	public void delete(Member member) {
		em.remove(member);
	}
	
	// 전체 조회 
	public List<Member> findAll() {
		// JPQL
		List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
		return result;
	}
	
	public Optional<Member> findById(Long id) {
		Member member = em.find(Member.class, id);
		return Optional.ofNullable(member);
	}
	
	public long count() {
		return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
	}
	
	// 단건 조회 
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
		List<Member> result = em.createQuery("select m from Member m where m.username = :username and m.age > :age", Member.class)
				.setParameter("username", username)
				.setParameter("age", age)
				.getResultList();
		return result;
	}
	
	public List<Member> findByUsername(String username) {
		return em.createNamedQuery("Member.findByUsername", Member.class).setParameter("username", username).getResultList();
	}
	
}
