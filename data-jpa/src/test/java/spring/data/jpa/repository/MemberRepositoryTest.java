package spring.data.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import spring.data.jpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Test
	void test() {
		
		Member member = new Member("MemberA");
		
		Member savedMember = memberRepository.save(member);
		
		Member findMember = memberRepository.findById(savedMember.getId()).get();
		
		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
		
	}

	@Test
	public void basicCRUD() {
		
		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		Member findMember1 = memberRepository.findById(member1.getId()).get();
		Member findMember2 = memberRepository.findById(member2.getId()).get();
		
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);
		
		// list 조회 검증 
		List<Member> members = memberRepository.findAll();
		assertThat(members.size()).isEqualTo(2);
		
		// count 검증
		long cnt = memberRepository.count();
		assertThat(cnt).isEqualTo(2);
		
		// delete 검증
		memberRepository.delete(member1);
		memberRepository.delete(member2);
		
		long beforeCnt = memberRepository.count();
		assertThat(beforeCnt).isEqualTo(0);
		
	}
	
}
