package spring.data.jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import spring.data.jpa.dto.MemberDTO;
import spring.data.jpa.entity.Member;
import spring.data.jpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberRepository memberRepository;
	
	@GetMapping("/members/{id}")
	public String findMember(@PathVariable("id") Long id) {
		Member member = memberRepository.findById(id).get();
		return member.getUsername();
	}
	
	// 도메인 클래스 컨버터 
	@GetMapping("/members2/{id}")
	public String findMember2(@PathVariable("id") Member member) {
		return member.getUsername();
	}
	
	@GetMapping("/members")
	public Page<MemberDTO> list(@PageableDefault(size = 5) Pageable pageable) {
		// entity 그대로 외부로 반환하면 X
		Page<Member> page = memberRepository.findAll(pageable);
//		Page<MemberDTO> map = page.map(m -> new MemberDTO(m.getId(), m.getUsername(), null));
//		Page<MemberDTO> map = page.map(m -> new MemberDTO(m));
		Page<MemberDTO> map = page.map(MemberDTO::new);
		return map;
	}
	
	@PostConstruct
	public void init() {
		for ( int i=1; i<=100; i++ ) memberRepository.save(new Member("member" + i, i));
	}
	
}
