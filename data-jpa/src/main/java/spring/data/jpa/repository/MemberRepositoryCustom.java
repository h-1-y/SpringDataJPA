package spring.data.jpa.repository;

import java.util.List;

import spring.data.jpa.entity.Member;

public interface MemberRepositoryCustom {

	List<Member> findMemberCustom();
	
}
