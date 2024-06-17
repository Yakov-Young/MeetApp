package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.Entities.Emuns.TypeStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.MeetUser;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.Repositories.IMeetUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetUserService {
    private final IMeetUserRepository meetUserRepository;
    private final UserService userService;
    private final MeetService meetService;

    @Autowired
    public MeetUserService(IMeetUserRepository meetUserRepository, UserService userService,
                           MeetService meetService) {
        this.meetUserRepository = meetUserRepository;
        this.userService = userService;
        this.meetService = meetService;
    }

    public MeetUser viewMeet(Long meetId,Long userId) {
        MeetUser meetUser = new MeetUser();

        meetUser.setUser(userService.getById(userId));
        meetUser.setMeet(meetService.getById(meetId));

        if (meetUser.getUser() == null || meetUser.getMeet() == null)
            return null;

        meetUser.setCreatedAt(LocalDateTime.now());
        meetUser.setTypeAction(TypeStatus.VIEW);

        return meetUserRepository.save(meetUser);
    }

    public MeetUser visitMeet(Long meetId, Long userId) {
        User user = userService.getById(userId);
        Meet meet = meetService.getById(meetId);

        if (user == null || meet == null || LocalDateTime.now().isAfter(meet.getDateStart()))
            return null;

        List<MeetUser> meetUserList = meetUserRepository.findByUserAndMeet(user, meet);

        for (MeetUser mu: meetUserList) {
            if (mu.getTypeAction().equals(TypeStatus.VISIT)) {
                return null;
            }
        }

        MeetUser meetUser = new MeetUser();
        meetUser.setMeet(meet);
        meetUser.setUser(user);

        meetUser.setCreatedAt(LocalDateTime.now());
        meetUser.setTypeAction(TypeStatus.VISIT);

        return meetUserRepository.save(meetUser);
    }

    public boolean noVisitMeet(Long meetId, Long userId) {
        User user = userService.getById(userId);
        Meet meet = meetService.getById(meetId);

        int counter = 0;
        for (MeetUser mu: meetUserRepository.findByMeet(meet)) {
            if (mu.getTypeAction().equals(TypeStatus.VISIT))
                counter++;
        }

        if (user == null || meet == null || LocalDateTime.now().isAfter(meet.getDateStart())
                || counter == meet.getCount())
            return false;

        List<MeetUser> meetUserList = meetUserRepository.findByUserAndMeet(user, meet);

        for (MeetUser mu: meetUserList) {
            if (mu.getTypeAction().equals(TypeStatus.VISIT)) {
                meetUserRepository.delete(mu);
                return true;
            }
        }
        return false;
    }
}
