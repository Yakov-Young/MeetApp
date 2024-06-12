package com.kemsu.sibiryakov.api.Repositories;

import com.kemsu.sibiryakov.api.Entities.UserPart.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
}
