package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.MeetDTO.AgreeMeetDTO;
import com.kemsu.sibiryakov.api.DTOs.MeetDTO.CreateMeetDTO;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.MeetPart.MeetStatus;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import com.kemsu.sibiryakov.api.Repositories.IMeetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetService {
    private final IMeetRepository meetRepository;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final OrganizerService organizerService;
    private final MeetStatusService meetStatusService;
    private final PlaceService placeService;

    private final AdministrationService administrationService;

    @Autowired
    public MeetService(IMeetRepository meetRepository, CategoryService categoryService,
                       CityService cityService, OrganizerService organizerService,
                       MeetStatusService meetStatusService, PlaceService placeService,
                       AdministrationService administrationService) {
        this.meetRepository = meetRepository;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.organizerService = organizerService;
        this.meetStatusService = meetStatusService;
        this.placeService = placeService;
        this.administrationService = administrationService;
    }

    public List<Meet> getAll() {
        return meetRepository.findAll();
    }

    public Meet getById(Long id) {
        return meetRepository.findById(id).orElse(null);
    }

    public Meet create(Meet meet) {
        return meetRepository.save(meet);
    }

    public Meet createMeet(Long organizerId, Meet meet) {
        meet.setOwner(
                organizerService.getById(organizerId)
        );

        meet.setStatus(
                meetStatusService.createStatus(meet.getStatus())
        );

        meet.setPlace(
                placeService.create(meet.getPlace())
        );

        return meetRepository.save(meet);
    }

    public Meet prepareToCreate(CreateMeetDTO createMeetDTO) {
        Meet meet = new Meet(
                createMeetDTO.getTitle(),
                createMeetDTO.getDescription(),
                createMeetDTO.getAdditionalInfo()
        );

        long dayPeriod = Duration.between(createMeetDTO.getDateStart(), createMeetDTO.getDateEnd()).toDays();

        if (dayPeriod >= Duration.ofDays(1L).toDays()) {
            return null;
        } else {
            meet.setDateStart(createMeetDTO.getDateStart());
            meet.setDateEnd(createMeetDTO.getDateEnd());
        }

        meet.setStatus(new MeetStatus().setWait());

        meet.setPlace(new Place(
                cityService.getById(createMeetDTO.getPlace().getCity_id()),
                createMeetDTO.getPlace().getStreet(),
                createMeetDTO.getPlace().getNumber(),
                createMeetDTO.getPlace().getApartment()
        )); // information about working place or living place organizer

        if (createMeetDTO.getCount() == null) {
            return null;
        } else {
            meet.setCount(createMeetDTO.getCount());
        }

        if (createMeetDTO.getCost() == null) {
            meet.setCost((short) 0);
        } else {
            meet.setCost(createMeetDTO.getCost());
        }

        meet.setCreatedAt(LocalDateTime.now());

        meet.setCategories(categoryService.getByManyId(createMeetDTO.getCategory()));

        return meet;

    }

    public Meet approvalMeet(AgreeMeetDTO agreeMeetDTO, Long id) {
        Meet meet = this.getById(agreeMeetDTO.getId());

        administrationService.getById(id);

        MeetStatus status = meet.getStatus()
                .setAgreement();
        status.setUser(administrationService.getById(id));

        meet.setStatus(status);

        return meetRepository.save(meet);
    }
}
