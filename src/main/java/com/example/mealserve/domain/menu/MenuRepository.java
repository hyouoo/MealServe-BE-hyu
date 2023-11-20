package com.example.mealserve.domain.menu;

import com.example.mealserve.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByName(String menu);
}
