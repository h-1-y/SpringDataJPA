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
	
	@Test
	public void findByUsernameAndAgeGreaterThen() {
		
		Member member1 = new Member("member1", 10);
		Member member2 = new Member("member1", 20);
		
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);
		
		List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("member1", 15);
		
		assertThat(result.get(0).getUsername()).isEqualTo("member1");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		
		assertThat(result.size()).isEqualTo(1);
		
	}
	
	@Test
	public void findByUsernameForNamedQuery() {
		
		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);
		
		List<Member> result = memberJpaRepository.findByUsername("member1");
		
		assertThat(result.get(0).getUsername()).isEqualTo("member1");
		assertThat(result.size()).isEqualTo(1);
		
	}

	@Test
	public void memberPaging() {
		
		// given
		memberJpaRepository.save(new Member("member1", 10));
		memberJpaRepository.save(new Member("member2", 10));
		memberJpaRepository.save(new Member("member3", 10));
		memberJpaRepository.save(new Member("member4", 10));
		memberJpaRepository.save(new Member("member5", 10));
		memberJpaRepository.save(new Member("member6", 10));
		memberJpaRepository.save(new Member("member7", 10));
		memberJpaRepository.save(new Member("member8", 10));
		memberJpaRepository.save(new Member("member9", 10));
		memberJpaRepository.save(new Member("member10", 10));
		
		int age = 10;
		int offset = 0;
		int limit = 3;
		
		// when 
		List<Member> result = memberJpaRepository.findByPaging(age, offset, limit);
		long totalCount = memberJpaRepository.totalCount(age);
		
		// then
		assertThat(result.size()).isEqualTo(3);
		assertThat(totalCount).isEqualTo(10);
		
	}
	
}
