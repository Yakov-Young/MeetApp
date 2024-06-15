package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.Entities.MeetPart.ContentStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.MeetStatus;
import com.kemsu.sibiryakov.api.Repositories.ICategoriesRepository;
import com.kemsu.sibiryakov.api.Repositories.IContentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentStatusService {
    private final IContentStatusRepository categoriesRepository;

    @Autowired
    public ContentStatusService(IContentStatusRepository contentStatusRepository) {
        this.categoriesRepository = contentStatusRepository;
    }

    public ContentStatus createStatus(ContentStatus status) {
        return categoriesRepository.save(status);
    }
}
