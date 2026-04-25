package com.github.superrelax102.reservationsystem.repository;

import com.github.superrelax102.reservationsystem.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long>{

}
