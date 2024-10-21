package spring.data.jpa.repository;

import org.springframework.beans.factory.annotation.Value;

// 인터페이스 기반의 Projections
public interface UsernameOnly {

//	@Value("#{target.username + ' ' + target.age}")
	String getUsername();
	
}
