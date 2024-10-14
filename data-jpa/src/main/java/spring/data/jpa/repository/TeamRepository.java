package spring.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.data.jpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
