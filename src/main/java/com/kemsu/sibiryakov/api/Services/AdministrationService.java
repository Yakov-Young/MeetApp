package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.AccessDTO.AccessDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.AdministrationRegisterDTO;
import com.kemsu.sibiryakov.api.Entities.UserPart.Access;
import com.kemsu.sibiryakov.api.Entities.UserPart.Administration;
import com.kemsu.sibiryakov.api.Entities.UserPart.AdministrationPhoneNumber;
import com.kemsu.sibiryakov.api.Repositories.IAdministrationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdministrationService {

    private final IAdministrationsRepository administrationsRepository;
    private final AccessService accessService;
    private final CityService cityService;
    private final AdministrationPhoneNumberService administrationPhoneNumberService;

    @Autowired
    public AdministrationService(IAdministrationsRepository administrationsRepository, AccessService accessService,
                                 CityService cityService, AdministrationPhoneNumberService administrationPhoneNumberService) {
        this.administrationsRepository = administrationsRepository;
        this.accessService = accessService;
        this.cityService = cityService;
        this.administrationPhoneNumberService = administrationPhoneNumberService;
    }

    public Administration getById(Long id) {
        return administrationsRepository.findById(id).orElse(null);
    }

    public Administration getByAccess(Access access) {
        return administrationsRepository.findByAccess(access);
    }

    public Administration createAdministration(Administration administration) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Access existingUser = accessService.getByLogin(administration.getAccess().getLogin());

        if (existingUser != null) {
            return null;
        }

        administration.setAccess(accessService.createAccess(
                new AccessDTO(
                        administration.getAccess().getLogin(),
                        administration.getAccess().getPassword()
                )
        ));

        administrationsRepository.save(administration);

        administration.setPhones(administrationPhoneNumberService.createManyPhone(administration.getPhones()));

        return administration;
    }

    public Administration prapareToRegisterAdministration(AdministrationRegisterDTO administrationRegisterDTO) {
        if (administrationRegisterDTO.getPassword().equals(administrationRegisterDTO.getCheckPassword())) {
            Administration administration = new Administration(administrationRegisterDTO.getName());

            LocalDateTime editTime = LocalDateTime.now();
            administration.setCreatedAt(editTime);
            administration.setLastActivity(editTime);

            administration.setCity(cityService.getById(administrationRegisterDTO.getCity()
            )); // information about working place or living place organizer

            List<AdministrationPhoneNumber> numbers = new ArrayList<>(); // information about phone number of organizer

            for (String n : administrationRegisterDTO.getPhones()) {
                numbers.add(new AdministrationPhoneNumber(n, administration));
            }
            administration.setPhones(numbers);

            administration.setAccess(new Access(
                    administrationRegisterDTO.getEmail(),
                    administrationRegisterDTO.getPassword()
            )); // data for access

            return administration;
        }
        return null;
    }
}
