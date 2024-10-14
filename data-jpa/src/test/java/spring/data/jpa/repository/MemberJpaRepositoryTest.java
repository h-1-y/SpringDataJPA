package spring.data.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import spring.data.jpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(true)
class MemberJpaRepositoryTest {

	@Autowired
	private MemberJpaRepository memberJpaRepository;
	
	@Test
	void testSave() {
		
		Member member = new Member("username test");
		
		Member savedMember = memberJpaRepository.save(member);
		
		Member findMember = memberJpaRepository.find(savedMember.getId());
		
		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
		
	}

	@Test
	public void basicCRUD() {
		
		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);
		
		Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
		Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
		
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);
		
		// list 조회 검증 
		List<Member> members = memberJpaRepository.findAll();
		assertThat(members.size()).isEqualTo(2);
		
		// count 검증
		long cnt = memberJpaRepository.count();
		assertThat(cnt).isEqualTo(2);
		
		// delete 검증
		memberJpaRepository.delete(member1);
		memberJpaRepository.delete(member2);
		
		long beforeCnt = memberJpaRepository.count();
		assertThat(beforeCnt).isEqualTo(0);
		
	}

}
