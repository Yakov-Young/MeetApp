package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.Entities.UserPart.UserOrganizerStatus;
import com.kemsu.sibiryakov.api.Repositories.IUserOrganizerStatusesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOrganizerStatusService {
    private final IUserOrganizerStatusesRepository userOrganizerStatusesRepository;

    @Autowired
    public UserOrganizerStatusService(IUserOrganizerStatusesRepository userOrganizerStatusesRepository) {
        this.userOrganizerStatusesRepository = userOrganizerStatusesRepository;
    }

    public UserOrganizerStatus createStatus(UserOrganizerStatus status) {
        return userOrganizerStatusesRepository.save(status);
    }
}
