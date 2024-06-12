package com.kemsu.sibiryakov.api.Repositories;

import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository extends JpaRepository<User, Long> {
}
