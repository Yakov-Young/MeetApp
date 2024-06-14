package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.AccessDTO.AccessDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.OrganizerRegisterDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.Gender;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import com.kemsu.sibiryakov.api.Entities.UserPart.*;
import com.kemsu.sibiryakov.api.Repositories.IAccessRepository;
import com.kemsu.sibiryakov.api.Repositories.IOrganizerRepository;
import com.kemsu.sibiryakov.api.Repositories.IUserOrganizerStatusesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizerService {
    private final IOrganizerRepository organizerRepository;
    private final OrganizerPhoneNumberService organizerPhoneNumberService;
    private final AccessService accessService;
    private final PlaceService placeService;
    private final CityService cityService;
    private final UserOrganizerStatusService userOrganizerStatusService;


    @Autowired
    public OrganizerService(IOrganizerRepository organizerRepository, OrganizerPhoneNumberService organizerPhoneNumberService,
                            AccessService accessService, PlaceService placeService,
                            CityService cityService, UserOrganizerStatusService userOrganizerStatusService) {
        this.organizerRepository = organizerRepository;
        this.organizerPhoneNumberService = organizerPhoneNumberService;
        this.accessService = accessService;
        this.placeService = placeService;
        this.cityService = cityService;
        this.userOrganizerStatusService = userOrganizerStatusService;
    }

    public List<Organizer> getAllOrganizer() {
        return organizerRepository.findAll();
    }
    public Organizer getById(Long id) {
        return organizerRepository.findById(id).orElse(null);
    }
    public Organizer save(Organizer organizer) {
        return organizerRepository.save(organizer);
    }
    public Organizer getByAccess(Access access) {
        return organizerRepository.getByAccess(access).orElse(null);
    }
//    public Organizer createOrganizer(OrganizerRegisterDTO organizerRegisterDTO) {
//        Access existingUser = accessRepository.findByLogin(organizerRegisterDTO.getEmail()).orElse(null);
//
//        if (existingUser != null) {
//            return null;
//        }
//
//        Organizer organizer = new Organizer(organizerRegisterDTO.getName(), organizerRegisterDTO.getSurname(),
//                organizerRegisterDTO.getPatronymic(), organizerRegisterDTO.getCompany(),
//                organizerRegisterDTO.getBirthday());
//
//        if (organizerRegisterDTO.getPassword().equals(organizerRegisterDTO.getCheckPassword())) {
//            Access access = new Access(organizerRegisterDTO.getEmail(),
//                    organizerRegisterDTO.getPassword(), "123456");
//            access = accessRepository.save(access);
//            organizer.setAccess(access);
//        } else {
//            return null;
//        }
//
//        UserOrganizerStatus status = new UserOrganizerStatus().setDefault();
//        status = organizerStatusesRepository.save(status);
//        organizer.setStatus(status);
//
//        Place place = placeService.create(organizerRegisterDTO.getPlace());
//        organizer.setPlace(place);
//
//        organizer.setGender(Gender.UNDEFINED);
//
//        LocalDateTime editTime = LocalDateTime.now();
//        organizer.setCreatedAt(editTime);
//        organizer.setLastActivity(editTime);
//
//        organizer = organizerRepository.save(organizer);
//
//        List<OrganizerPhoneNumber> numbers = new ArrayList<>();
//
//        for (String n : organizerRegisterDTO.getPhones()) {
//            numbers.add(new OrganizerPhoneNumber(n, organizer));
//        }
//        organizer.setPhones(organizerPhoneNumberService.createManyPhone(numbers));
//
//        return organizer;
//    }

    public Organizer createOrganizer(Organizer organizer) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Access existingUser = accessService.getByLogin(organizer.getAccess().getLogin());

        if (existingUser != null) {
            return null;
        }

        organizer.setAccess(accessService.createAccess(
                new AccessDTO(
                        organizer.getAccess().getLogin(),
                        organizer.getAccess().getPassword()
                )
        ));

        organizer.setStatus(userOrganizerStatusService.createStatus(
                organizer.getStatus()
        ));

        organizer.setPlace(placeService.create(
                organizer.getPlace()
        ));

        organizer = organizerRepository.save(organizer);

        organizer.setPhones(organizerPhoneNumberService.createManyPhone(organizer.getPhones()));

        return organizer;
    }

    public Organizer prepareToRegisterOrganizer(OrganizerRegisterDTO organizerRegisterDTO) {
        if (organizerRegisterDTO.getPassword().equals(organizerRegisterDTO.getCheckPassword())) {
            Organizer organizer = new Organizer(organizerRegisterDTO.getName(), organizerRegisterDTO.getSurname(),
                    organizerRegisterDTO.getPatronymic(), organizerRegisterDTO.getCompany(),
                    organizerRegisterDTO.getBirthday());

            organizer.setGender(Gender.UNDEFINED);
            organizer.setStatus(new UserOrganizerStatus().setDefault());

            LocalDateTime editTime = LocalDateTime.now();
            organizer.setCreatedAt(editTime);
            organizer.setLastActivity(editTime);

            organizer.setPlace(new Place(
                    cityService.getById(organizerRegisterDTO.getPlace().getCity_id()),
                    organizerRegisterDTO.getPlace().getStreet(),
                    organizerRegisterDTO.getPlace().getNumber(),
                    organizerRegisterDTO.getPlace().getApartment()
            )); // information about working place or living place organizer

            List<OrganizerPhoneNumber> numbers = new ArrayList<>(); // information about phone number of organizer

            for (String n : organizerRegisterDTO.getPhones()) {
                numbers.add(new OrganizerPhoneNumber(n, organizer));
            }
            organizer.setPhones(numbers);

            organizer.setAccess(new Access(
                    organizerRegisterDTO.getEmail(),
                    organizerRegisterDTO.getPassword()
            )); // data for access

            return organizer;
        }

        return null;
    }
}
