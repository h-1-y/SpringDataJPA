package spring.data.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import spring.data.jpa.dto.MemberDTO;
import spring.data.jpa.entity.Member;
import spring.data.jpa.entity.Team;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	TeamRepository teamRepository;
	
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
	
	@Test
	public void findByUsernameAndAgeGreaterThen() {
		
		Member member1 = new Member("member1", 10);
		Member member2 = new Member("member1", 20);
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("member1", 15);
		
		assertThat(result.get(0).getUsername()).isEqualTo("member1");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		
		assertThat(result.size()).isEqualTo(1);
		
	}
	
	@Test
	public void findHelloBy() {
		List<Member> result = memberRepository.findHelloBy();
		
		List<Member> result2 = memberRepository.findTop3HelloBy();
	}
	
	@Test
	public void findByUsernameForNamedQuery() {
		
		Member member1 = new Member("AAA");
		Member member2 = new Member("BBB");
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		List<Member> result = memberRepository.findByUsername("AAA");
		
		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.size()).isEqualTo(1);
		
	}
	
	@Test
	public void findUser() {
		
		Member member1 = new Member("A", 10);
		Member member2 = new Member("B", 20);
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		List<Member> result = memberRepository.findUser("A", 10);
		
		assertThat(result.get(0)).isEqualTo(member1);
		
	}
	
	@Test
	public void findUsername() {
		
		Member member1 = new Member("A", 10);
		Member member2 = new Member("B", 20);
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		List<String> result = memberRepository.findUsername();
		
		for ( String name : result ) System.out.println("name === " + name);
		
	}
	
	@Test
	public void findMemberDto() {
		
		
		Team teamA = new Team("team A");
		Team teamB = new Team("team B");
		
		teamRepository.save(teamA);
		teamRepository.save(teamB);
		
		Member member1 = new Member("A", 10, teamA);
		Member member2 = new Member("B", 20, teamB);
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		List<MemberDTO> result = memberRepository.findMemberDto();
		
		for ( MemberDTO dto : result ) System.out.println("dto.toString() ==== " + dto.toString());
		
	}
	
	@Test
	public void findByNamesForCollectionParameter() {
		
		Member member1 = new Member("AAA", 10);
		Member member2 = new Member("BBB", 20);
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
		
	}
	
	@Test
	public void findByUsernameForReturnType() {
		
		Member member1 = new Member("AAA", 10);
		Member member2 = new Member("BBB", 20);
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
//		List<Member> result = memberRepository.findListByUsername("AAA");
//	 	Member findMember = memberRepository.findMemberByUsername("AAA");
		Optional<Member> optionalMember = memberRepository.findOptionalByUsername("AAA");
		
	}
	
	@Test
	public void memberPaging() {
		
		// given
		memberRepository.save(new Member("member", 10));
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 10));
		memberRepository.save(new Member("member3", 10));
		memberRepository.save(new Member("member4", 10));
		memberRepository.save(new Member("member5", 10));
		memberRepository.save(new Member("member6", 10));
		memberRepository.save(new Member("member7", 10));
		memberRepository.save(new Member("member8", 10));
		memberRepository.save(new Member("member9", 10));
		
		int age = 10;
		// page index는 0 부터 
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
		
		// when 
//		Page<Member> page = memberRepository.findByAge(age, pageRequest);
		Page<Member> page = memberRepository.findByAge(age, pageRequest);
		
		// DTO 변환 
		Page<MemberDTO> toMap = page.map(m -> new MemberDTO(m.getId(), m.getUsername(), null));
		
		// then
		List<Member> result = page.getContent();
		long totalCount = page.getTotalElements();
		
		System.out.println("totalCount ====== " + totalCount);
		for ( Member m : result ) System.out.println("m ======= " + m);
		
		assertThat(result.size()).isEqualTo(3);
		assertThat(totalCount).isEqualTo(10);
		assertThat(page.getNumber()).isEqualTo(0);
		assertThat(page.getTotalPages()).isEqualTo(4);
		assertThat(page.isFirst()).isTrue();
		assertThat(page.hasNext()).isTrue();
		
	}
	
	@Test
	public void memberPagingSlice() {
		
		// given
		memberRepository.save(new Member("member", 10));
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 10));
		memberRepository.save(new Member("member3", 10));
		memberRepository.save(new Member("member4", 10));
		memberRepository.save(new Member("member5", 10));
		memberRepository.save(new Member("member6", 10));
		memberRepository.save(new Member("member7", 10));
		memberRepository.save(new Member("member8", 10));
		memberRepository.save(new Member("member9", 10));
		
		int age = 10;
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
		
		// when 
		// 비동기처리시 사용 (ex. 더보기, 스크롤 페이징 등등)
		Slice<Member> page = memberRepository.findSliceByAge(age, pageRequest);
		
		// DTO 변환 
		Slice<MemberDTO> toMap = page.map(m -> new MemberDTO(m.getId(), m.getUsername(), null));
		
		// then
		List<Member> result = page.getContent();
		
		for ( Member m : result ) System.out.println("m ======= " + m);
		
		assertThat(result.size()).isEqualTo(3);
		assertThat(page.getNumber()).isEqualTo(0);
		assertThat(page.isFirst()).isTrue();
		assertThat(page.hasNext()).isTrue();
		
	}
	
}
