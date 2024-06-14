package com.kemsu.sibiryakov.api.Repositories;

import com.kemsu.sibiryakov.api.Entities.UserPart.OrganizerPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrganizerPhoneNumberRepository extends JpaRepository<OrganizerPhoneNumber, Long> {
    Optional<OrganizerPhoneNumber> findByPhone(String phone);

}
