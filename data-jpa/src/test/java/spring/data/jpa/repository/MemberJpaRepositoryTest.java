package spring.data.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

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

	

}
