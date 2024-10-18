package spring.data.jpa.dto;

import lombok.Data;
import spring.data.jpa.entity.Member;

@Data
public class MemberDTO {

	private Long id;
	private String username;
	private String teamName;
	
	public MemberDTO(Long id, String username, String teamName) {
		super();
		this.id = id;
		this.username = username;
		this.teamName = teamName;
	}
	
	// entity -> DTO 변환
	public MemberDTO(Member member) {
		this.id = member.getId();
		this.username = member.getUsername();
	}
	
}
