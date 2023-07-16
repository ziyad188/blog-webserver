package net.blog.springbootrestapi.repository;

import net.blog.springbootrestapi.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByName(String name);
}
