package spring.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.data.jpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {

}
