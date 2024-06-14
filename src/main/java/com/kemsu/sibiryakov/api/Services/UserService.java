package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.AccessDTO.AccessDTO;
import com.kemsu.sibiryakov.api.DTOs.RegisterDTO.UserRegisterDTO;
import com.kemsu.sibiryakov.api.Entities.Emuns.Gender;
import com.kemsu.sibiryakov.api.Entities.UserPart.Access;
import com.kemsu.sibiryakov.api.Entities.UserPart.User;
import com.kemsu.sibiryakov.api.Entities.UserPart.UserOrganizerStatus;
import com.kemsu.sibiryakov.api.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final IUsersRepository usersRepository;
    private final UserOrganizerStatusService userOrganizerStatusService;
    private final RoleService roleService;
    private final AccessService accessService;
    private final CityService cityService;

    @Autowired
    public UserService(IUsersRepository usersRepository, AccessService accessService,
                       UserOrganizerStatusService userOrganizerStatusService,
                       RoleService roleService, CityService cityService) {
        this.usersRepository = usersRepository;
        this.accessService = accessService;
        this.userOrganizerStatusService = userOrganizerStatusService;
        this.roleService = roleService;
        this.cityService = cityService;
    }

    public List<User> getAll() {
        return usersRepository.findAll();
    }

    public User getById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public User getByAccess(Access access) {
        return usersRepository.findByAccess(access).orElse(null);
    }

    public User save(User user) {
        return usersRepository.save(user);
    }

    public User createVisitor(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Access existingUser = accessService.getByLogin(user.getAccess().getLogin());

        if (existingUser != null) {
            return null;
        }

        user.setAccess(accessService.createAccess(
                new AccessDTO(
                        user.getAccess().getLogin(),
                        user.getAccess().getPassword()
                )
        ));
        user.setStatus(userOrganizerStatusService.createStatus(
                user.getStatus()
        ));
        user.setCity(cityService.getById(6L)); // Kemerovo city
        user.setRole(roleService.getByID(1L)); // User role

        return usersRepository.save(user);
    }

    public User createModerator(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
            Access existingUser = accessService.getByLogin(user.getAccess().getLogin());

            if (existingUser != null) {
                return null;
            }

            user.setAccess(accessService.createAccess(
                    new AccessDTO(
                            user.getAccess().getLogin(),
                            user.getAccess().getPassword()
                    )
            ));
            user.setStatus(userOrganizerStatusService.createStatus(
                    user.getStatus()
            ));
            user.setCity(cityService.getById(6L)); // Kemerovo city
            user.setRole(roleService.getByID(2L)); // Moderator role

            return usersRepository.save(user);
    }

    public User prepareToRegisterUser(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO.getPassword().equals(userRegisterDTO.getCheckPassword())) {
            User user = new User(userRegisterDTO.getName(), userRegisterDTO.getSurname(),
                    userRegisterDTO.getPatronymic(), userRegisterDTO.getBirthday());

            user.setGender(Gender.UNDEFINED);
            user.setStatus(new UserOrganizerStatus().setDefault());

            LocalDateTime editTime = LocalDateTime.now();
            user.setCreatedAt(editTime);
            user.setLastActivity(editTime);

            user.setAccess(new Access(userRegisterDTO.getEmail(), userRegisterDTO.getPassword()));

            return user;
        }

        return null;
    }
}
