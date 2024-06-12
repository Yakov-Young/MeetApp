package com.kemsu.sibiryakov.api.Repositories;

import com.kemsu.sibiryakov.api.Entities.UserPart.Access;
import com.kemsu.sibiryakov.api.Entities.UserPart.Organizer;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrganizerRepository extends JpaRepository<Organizer, Long> {
    @NotNull
    Optional<Organizer> findById(@NotNull Long id);

    Optional<Organizer> getByAccess(Access access);
}
