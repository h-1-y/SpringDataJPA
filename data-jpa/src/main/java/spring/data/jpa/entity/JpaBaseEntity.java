package spring.data.jpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
public class JpaBaseEntity {

	@Column(updatable = false) // updatable false <- 업데이트를 허용하지 않음
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	
	@PrePersist
	public void prePersist() {
		
		LocalDateTime now = LocalDateTime.now();
		
		this.createDate = now;
		this.updateDate = now;
		
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updateDate = LocalDateTime.now();
	}
	
}
