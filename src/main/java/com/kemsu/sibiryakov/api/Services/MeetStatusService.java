package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.Entities.MeetPart.MeetStatus;
import com.kemsu.sibiryakov.api.Repositories.IMeetRepository;
import com.kemsu.sibiryakov.api.Repositories.IMeetStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetStatusService {
    private final IMeetStatusRepository meetStatusRepository;

    @Autowired
    public MeetStatusService(IMeetStatusRepository meetStatusRepository) {
        this.meetStatusRepository = meetStatusRepository;
    }

    public MeetStatus createStatus(MeetStatus status) {
        return meetStatusRepository.save(status);
    }
}
