package com.kemsu.sibiryakov.api.Repositories;

import com.kemsu.sibiryakov.api.Entities.Emuns.TypeStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.MeetUser;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMeetUserRepository extends JpaRepository<MeetUser, Long> {
    List<MeetUser> findByUserAndMeet(User user, Meet meet);

    List<MeetUser> findByMeet(Meet meet);

    List<MeetUser> findByUser(User user);
}
