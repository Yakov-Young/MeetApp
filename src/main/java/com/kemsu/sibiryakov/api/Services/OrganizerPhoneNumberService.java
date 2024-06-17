package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.PhoneAddManyDTO;
import com.kemsu.sibiryakov.api.Entities.UserPart.OrganizerPhoneNumber;
import com.kemsu.sibiryakov.api.Repositories.IOrganizerPhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizerPhoneNumberService {
    private final IOrganizerPhoneNumberRepository organizerPhoneNumberRepository;

    @Autowired
    public OrganizerPhoneNumberService(IOrganizerPhoneNumberRepository organizerPhoneNumberRepository) {
        this.organizerPhoneNumberRepository = organizerPhoneNumberRepository;
    }

    public List<OrganizerPhoneNumber> getAllPhone() {
        return organizerPhoneNumberRepository.findAll();
    }

    public OrganizerPhoneNumber getById(Long id) {
        return organizerPhoneNumberRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        organizerPhoneNumberRepository.deleteById(id);
    }

    public void deleteAll() {
        organizerPhoneNumberRepository.deleteAll();
    }

    public OrganizerPhoneNumber getByPhone(String phone) {
        return organizerPhoneNumberRepository.findByPhone(phone).orElse(null);
    }

    public List<OrganizerPhoneNumber> getByManyId(List<Long> ids) {
        List<OrganizerPhoneNumber> numbers = new ArrayList<>();
        for (Long id : ids) {
            numbers.add(this.getById(id));
        }

        return numbers;
    }

    public List<OrganizerPhoneNumber> createManyPhone(List<OrganizerPhoneNumber> numbers) {
        return organizerPhoneNumberRepository.saveAll(numbers);
    }

    public List<OrganizerPhoneNumber> createManyPhone(PhoneAddManyDTO phoneAddManyDTO) {
        List<OrganizerPhoneNumber> numbers = new ArrayList<>();

//        for (String n : phoneAddManyDTO.getPhones()) {
//            numbers.add(new OrganizerPhoneNumber(n));
//        }

        return organizerPhoneNumberRepository.saveAll(numbers);
    }
}
