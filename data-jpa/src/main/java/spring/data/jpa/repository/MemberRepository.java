package spring.data.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import spring.data.jpa.dto.MemberDTO;
import spring.data.jpa.entity.Member;

// SpringDataJPA

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
	
	// 다양한 반환 타입 가능 
	List<Member> findListByUsername(String username);
	Member findMemberByUsername(String username);
	Optional<Member> findOptionalByUsername(String username);
	
	
	// SpringDataJPA Paging
	
	// 페이징 메소드 생성시 리스트 쿼리와 카운트 쿼리를 나눌수 있음 (카운트 쿼리는 조인테이블이 where절에 있지 않은 이상 조인이 필요하지 않기때문에 제공하는 기능인듯)
	// @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m) from Member m")
	Page<Member> findByAge(int age, Pageable pageable);
	// 비동기처리시 사용 (ex. 더보기, 스크롤 페이징 등등)
	Slice<Member> findSliceByAge(int age, Pageable pageable);
	
}
