package dev.suvera.scim2.example.server.jpa.repo;

import dev.suvera.scim2.example.server.jpa.entity.ScimGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScimGroupRepository extends JpaRepository<ScimGroup, Long> {
}
