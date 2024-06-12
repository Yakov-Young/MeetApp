package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.OrganizerRegisterDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.Gender;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import com.kemsu.sibiryakov.api.Entities.UserPart.Access;
import com.kemsu.sibiryakov.api.Entities.UserPart.Organizer;
import com.kemsu.sibiryakov.api.Entities.UserPart.OrganizerPhoneNumber;
import com.kemsu.sibiryakov.api.Entities.UserPart.UserOrganizerStatus;
import com.kemsu.sibiryakov.api.Repositories.IAccessRepository;
import com.kemsu.sibiryakov.api.Repositories.IOrganizerRepository;
import com.kemsu.sibiryakov.api.Repositories.IUserOrganizerStatusesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizerService {
    private final IOrganizerRepository organizerRepository;
    private final IAccessRepository accessRepository;
    private final IUserOrganizerStatusesRepository organizerStatusesRepository;
    private final OrganizerPhoneNumberService organizerPhoneNumberService;
    private final PlaceService placeService;

    @Autowired
    public OrganizerService(IOrganizerRepository organizerRepository, IAccessRepository accessRepository,
                            IUserOrganizerStatusesRepository organizerStatusesRepository,
                            OrganizerPhoneNumberService organizerPhoneNumberService, PlaceService placeService) {
        this.organizerRepository = organizerRepository;
        this.accessRepository = accessRepository;
        this.organizerStatusesRepository = organizerStatusesRepository;
        this.organizerPhoneNumberService = organizerPhoneNumberService;
        this.placeService = placeService;
    }

    public List<Organizer> getAllOrganizer() {
        return organizerRepository.findAll();
    }
    public Organizer getById(Long id) {
        return organizerRepository.findById(id).orElse(null);
    }

    public Organizer createOrganizer(OrganizerRegisterDTO organizerRegisterDTO) {
        Access existingUser = accessRepository.findByLogin(organizerRegisterDTO.getEmail()).orElse(null);

        if (existingUser != null) {
            return null;
        }

        Organizer organizer = new Organizer(organizerRegisterDTO.getName(), organizerRegisterDTO.getSurname(),
                organizerRegisterDTO.getPatronymic(), organizerRegisterDTO.getCompany(),
                organizerRegisterDTO.getBirthday());

        if (organizerRegisterDTO.getPassword().equals(organizerRegisterDTO.getCheckPassword())) {
            Access access = new Access(organizerRegisterDTO.getEmail(),
                    organizerRegisterDTO.getPassword(), "123456");
            access = accessRepository.save(access);
            organizer.setAccess(access);
        } else {
            return null;
        }

        UserOrganizerStatus status = new UserOrganizerStatus().setDefault();
        status = organizerStatusesRepository.save(status);
        organizer.setStatus(status);

        Place place = placeService.create(organizerRegisterDTO.getPlace());
        organizer.setPlace(place);

        organizer.setGender(Gender.UNDEFINED);

        LocalDateTime editTime = LocalDateTime.now();
        organizer.setCreatedAt(editTime);
        organizer.setLastActivity(editTime);

        organizer = organizerRepository.save(organizer);

        List<OrganizerPhoneNumber> numbers = new ArrayList<>();

        for (String n : organizerRegisterDTO.getPhones()) {
            numbers.add(new OrganizerPhoneNumber(n, organizer));
        }
        organizer.setPhones(organizerPhoneNumberService.createManyPhone(numbers));

        return organizer;
    }
}
