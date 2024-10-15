package spring.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import spring.data.jpa.dto.MemberDTO;
import spring.data.jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
	
	List<Member> findHelloBy();
	List<Member> findTop3HelloBy();
	
	// NamedQuery
	// 해당 어노테이션이 없더라도 entity명.메소드명으로 네임드 쿼리를 찾기때문에 생략해도 가능
	@Query(name = "Member.findByUsername")
	List<Member> findByUsername(@Param("username") String username);
	
	// 어노테이션으로 메소드에 바로 JPQL을 작성하는 방법
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username") String username, @Param("age") int age);
	
	@Query("select m.username from Member m")
	List<String> findUsername();
	
	// DTO 사용시 패키지명과 클래스명 명시 필요 
	@Query("select new spring.data.jpa.dto.MemberDTO(m.id, m.username, t.name) from Member m join m.team t")
	List<MemberDTO> findMemberDto();
	
	@Query("select m from Member m where m.username in :names")
	List<Member> findByNames(@Param("names") List<String> names);
	
}
