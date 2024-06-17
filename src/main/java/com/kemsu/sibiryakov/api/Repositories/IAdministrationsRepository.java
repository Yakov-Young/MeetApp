package com.kemsu.sibiryakov.api.Repositories;

import com.kemsu.sibiryakov.api.Entities.UserPart.Access;
import com.kemsu.sibiryakov.api.Entities.UserPart.Administration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdministrationsRepository extends JpaRepository<Administration, Long> {
    public Administration findByAccess(Access access);
}
