package spring.data.jpa.repository;

import org.springframework.data.jpa.domain.Specification;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import spring.data.jpa.entity.Member;
import spring.data.jpa.entity.Team;

public class MemberSpecification {

	public static Specification<Member> teamName(final String teamName) {
		
		return (Specification<Member>) (root, query, builder) -> {
				
			if ( StringUtils.isEmpty(teamName) ) return null;
			
			Join<Member, Team> t = root.join("team", JoinType.INNER);
			
			return builder.equal(t.get("name"), teamName);
			
		};
		
	}
	
	public static Specification<Member> username(final String username) {
		
		return (Specification<Member>) (root, query, builder) -> {
			return builder.equal(root.get("username"), username);
		};
		
	}
	
}
