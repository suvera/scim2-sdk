package dev.suvera.scim2.example.server.jpa.repo;

import dev.suvera.scim2.example.server.jpa.entity.ScimUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScimUserRepository extends JpaRepository<ScimUser, Long> {
}
