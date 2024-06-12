package com.kemsu.sibiryakov.api.Repositories;

import com.kemsu.sibiryakov.api.Entities.UserPart.Administration;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAdministrationsRepository extends JpaRepository<Administration, Long> {
    @NotNull
    Optional<Administration> findById(@NotNull Long id);
}
