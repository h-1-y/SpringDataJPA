package spring.data.jpa.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import spring.data.jpa.entity.Member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

	private final EntityManager em;
	
	@Override
	public List<Member> findMemberCustom() {
		List<Member> result = em.createQuery("select m from Member m where m.username = 'AAA'", Member.class).getResultList();
		return result;
	}
	
}
