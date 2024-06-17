package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.MeetDTO.AgreeMeetDTO;
import com.kemsu.sibiryakov.api.DTOs.BanDTO;
import com.kemsu.sibiryakov.api.DTOs.MeetDTO.CreateMeetDTO;
import com.kemsu.sibiryakov.api.Entities.Category;
import com.kemsu.sibiryakov.api.Entities.Emuns.TypeStatus;
import com.kemsu.sibiryakov.api.Entities.MeetPart.Meet;
import com.kemsu.sibiryakov.api.Entities.MeetPart.MeetStatus;
import com.kemsu.sibiryakov.api.Entities.MeetUser;
import com.kemsu.sibiryakov.api.Entities.PlacePart.Place;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.Repositories.IMeetRepository;
import com.kemsu.sibiryakov.api.Repositories.IMeetUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MeetService {
    private final IMeetRepository meetRepository;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final OrganizerService organizerService;
    private final MeetStatusService meetStatusService;
    private final PlaceService placeService;
    private final UserService userService;
    private final IMeetUserRepository meetUserRepository;
    private final AdministrationService administrationService;

    @Autowired
    public MeetService(IMeetRepository meetRepository, CategoryService categoryService,
                       CityService cityService, OrganizerService organizerService,
                       MeetStatusService meetStatusService, PlaceService placeService, UserService userService, IMeetUserRepository meetUserRepository,
                       AdministrationService administrationService) {
        this.meetRepository = meetRepository;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.organizerService = organizerService;
        this.meetStatusService = meetStatusService;
        this.placeService = placeService;
        this.userService = userService;
        this.meetUserRepository = meetUserRepository;
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

        MeetStatus status = meet.getStatus().setAgreement();

        status.setUser(administrationService.getById(id));

        meet.setStatus(status);

        return meetRepository.save(meet);
    }

    public Meet banMeet(BanDTO banDTO, Long id) {
        Meet meet = this.getById(banDTO.getId());

        MeetStatus status = meet.getStatus().setBanned();

        status.setUser(administrationService.getById(id));
        status.setNote(banDTO.getContent());
        status.setCreatedAt(LocalDateTime.now());

        return meetRepository.save(meet);
    }

    public List<Meet> getFutureMeetByPlace(Long administrationId) {
        List<Meet> meets = this.getAll();
        List<Meet> futureMeets = new ArrayList<>();

        for (Meet m: meets) {
            if (LocalDateTime.now().isBefore(m.getDateStart())) {
                futureMeets.add(m);
            }
        }

        return futureMeets;
    }

    public List<Meet> getLastMeetByPlace(Long administrationId) {
        List<Meet> meets = this.getAll();
        List<Meet> lastMeets = new ArrayList<>();

        for (Meet m: meets) {
            if (LocalDateTime.now().isAfter(m.getDateStart())) {
                lastMeets.add(m);
            }
        }

        return lastMeets;
    }

    public List<Meet> myMeet(Long organizerId) {
        List<Meet> meets = meetRepository.findByOwner(
                organizerService.getById(organizerId)
        );

        return meets;
    }

    public List<Meet> myFutureMeet(Long organizerId) {
        List<Meet> meets = this.myMeet(organizerId);

        List<Meet> futureMeets = new ArrayList<>();

        for (Meet m: meets) {
            if (LocalDateTime.now().isBefore(m.getDateStart())) {
                futureMeets.add(m);
            }
        }

        return futureMeets;
    }

    public List<Meet> myLastMeet(Long organizerId) {
        List<Meet> meets = this.myMeet(organizerId);

        List<Meet> lastMeets = new ArrayList<>();

        for (Meet m: meets) {
            if (LocalDateTime.now().isAfter(m.getDateStart())) {
                lastMeets.add(m);
            }
        }

        return lastMeets;

    }

    public Meet canceledMeet(BanDTO banDTO) {
        Meet meet = this.getById(banDTO.getId());

        MeetStatus status = meet.getStatus().setCanceled();

        status.setUser(administrationService.getById(banDTO.getId()));
        status.setNote(banDTO.getContent());

        meet.setStatus(status);

        List<MeetUser> meetUsers = meet.getMeetUser();

        for (MeetUser m: meetUsers) {
            User user = userService.getById(m.getUser().getId());
            Meet tempMeet = this.getById(banDTO.getId());

            int counter = 0;
            for (MeetUser mu: meetUserRepository.findByMeet(tempMeet)) {
                if (mu.getTypeAction().equals(TypeStatus.VISIT))
                    counter++;
            }

            if (user == null || tempMeet == null || LocalDateTime.now().isAfter(tempMeet.getDateStart())
                    || counter == tempMeet.getCount())
                continue;

            List<MeetUser> meetUserList = meetUserRepository.findByUserAndMeet(user, tempMeet);

            for (MeetUser mu: meetUserList) {
                if (mu.getTypeAction().equals(TypeStatus.VISIT)) {
                    meetUserRepository.delete(mu);
                }
            }
        }

        return meetRepository.save(meet);
    }

    public Set<Meet> getByCategory(Long categoryId) {
        Category category = categoryService.getById(categoryId);
        return meetRepository.findByCategories(category);
    }
}
