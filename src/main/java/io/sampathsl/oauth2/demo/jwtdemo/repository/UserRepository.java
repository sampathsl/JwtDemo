package io.sampathsl.oauth2.demo.jwtdemo.repository;

import java.util.Optional;

import io.sampathsl.oauth2.demo.jwtdemo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByUsername(String username);
}
