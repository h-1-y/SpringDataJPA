package spring.data.jpa.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

	@PersistenceContext
	EntityManager em;
	
	@Test
	void test() {
		
		Team teamA = new Team("Team A");
		Team teamB = new Team("Team B");
		
		em.persist(teamA);
		em.persist(teamB);
		
		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 20, teamA);
		Member member3 = new Member("member3", 30, teamB);
		Member member4 = new Member("member4", 40, teamB);
		
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
		
		// 초기화
		em.flush();
		em.clear();
		
		// 확인 
		List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
		
		for ( Member m : members ) {
			System.out.print("member === " + m);
			System.out.println(" / member.team ==== " + m.getTeam());
		}
		
	}

}
