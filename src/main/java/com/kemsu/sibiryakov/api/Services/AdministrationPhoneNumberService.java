package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.PhoneAddManyDTO;
import com.kemsu.sibiryakov.api.Entities.UserPart.AdministrationPhoneNumber;
import com.kemsu.sibiryakov.api.Repositories.IAdministrationPhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministrationPhoneNumberService {
    private final IAdministrationPhoneNumberRepository administrationPhoneNumberRepository;

    @Autowired
    public AdministrationPhoneNumberService(IAdministrationPhoneNumberRepository administrationPhoneNumberRepository) {
        this.administrationPhoneNumberRepository = administrationPhoneNumberRepository;
    }

    public List<AdministrationPhoneNumber> getAllPhone() {
        return administrationPhoneNumberRepository.findAll();
    }

    public void deleteAll() {
        administrationPhoneNumberRepository.deleteAll();
    }

    public List<AdministrationPhoneNumber> createManyPhone(List<AdministrationPhoneNumber> numbers) {
        return administrationPhoneNumberRepository.saveAll(numbers);
    }

    public List<AdministrationPhoneNumber> createManyPhone(PhoneAddManyDTO phoneAddManyDTO) {
        List<AdministrationPhoneNumber> numbers = new ArrayList<>();

//        for (String n : phoneAddManyDTO.getPhones()) {
//            numbers.add(new OrganizerPhoneNumber(n));
//        }

        return administrationPhoneNumberRepository.saveAll(numbers);
    }
}
