package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.Entities.UserPart.Administration;
import com.kemsu.sibiryakov.api.Repositories.IAdministrationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministrationService {

    private final IAdministrationsRepository repository;

    @Autowired
    public AdministrationService(IAdministrationsRepository repository) {
        this.repository = repository;
    }

    public Optional<Administration> getById(Long id) {
        return repository.findById(id);
    }
}
