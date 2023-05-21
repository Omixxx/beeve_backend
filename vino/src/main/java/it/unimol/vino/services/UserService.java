package it.unimol.vino.services;


import it.unimol.vino.dto.SectorDTO;
import it.unimol.vino.dto.SectorPermissionDTO;
import it.unimol.vino.dto.UserPermissionDTO;
import it.unimol.vino.dto.UserDTO;
import it.unimol.vino.exceptions.SectorNotFoundException;
import it.unimol.vino.exceptions.UnauthorizedAccessException;
import it.unimol.vino.exceptions.UserNotFoundException;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.UpdatePermissionsRequest;
import it.unimol.vino.models.response.UpdatePermissionResponse;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;

    @Transactional
    public UpdatePermissionResponse updatePermissions(UpdatePermissionsRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User updaterUser = this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("l'utente con email " + email + " non è stato trovato")
        );

        User updatedUser = this.userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException("l'utente con email " + request.getEmail() + " non è stato trovato")
        );

        if (!updaterUser.getRole().equals(Role.ADMIN))
            throw new UnauthorizedAccessException("L'utente " + email + " non ha i permessi per effettuare questa operazione");

        request.getPermissions().forEach((sectorName, permissions) -> {

            Sector sector = this.sectorRepository.findSectorBySectorName(sectorName).orElseThrow(
                    () -> new SectorNotFoundException("Il settore con il nome "
                            + sectorName
                            + " non appartiene alla lista dei settori: "
                            + Arrays.toString(SectorName.values()))
            );
            updatedUser.updatePermission(sector, permissions);
        });
        this.userRepository.save(updaterUser);
        return new UpdatePermissionResponse("Permessi aggiornati con successo!");
    }

    public List<UserPermissionDTO> getPermissions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("l'utente con email " + email + " non è stato trovato")
        );
        List<UserSectorPermission> userSectorPermission = user.getPermissions();
        List<UserPermissionDTO> permissions = new ArrayList<>();
        userSectorPermission.forEach(userSectorPermission1 -> {
            permissions.add(UserPermissionDTO.builder()
                    .permissions(Collections.singletonList(SectorPermissionDTO.builder()
                            .sector(SectorDTO.builder()
                                    .sectorName(userSectorPermission1.getSector().getSectorName())
                                    .build())
                            .canRead(userSectorPermission1.getCanRead())
                            .canWrite(userSectorPermission1.getCanWrite())
                            .canDelete(userSectorPermission1.getCanDelete())
                            .canUpdate(userSectorPermission1.getCanUpdate())
                            .build())
                    ).build());
        });
        return permissions;
    }

    public List<UserPermissionDTO> getAllPermissions() {
        return this.userRepository.findByRoleNot(Role.ADMIN).stream().map(
                user -> UserPermissionDTO.builder()
                        .user(UserDTO.builder()
                                .email(user.getEmail())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .build())
                        .permissions(user.getPermissions().stream().map(
                                userSectorPermission -> SectorPermissionDTO.builder()
                                        .sector(SectorDTO.builder()
                                                .sectorName(userSectorPermission.getSector().getSectorName())
                                                .build())
                                        .canRead(userSectorPermission.getCanRead())
                                        .canWrite(userSectorPermission.getCanWrite())
                                        .canDelete(userSectorPermission.getCanDelete())
                                        .canUpdate(userSectorPermission.getCanUpdate())
                                        .build()
                        ).toList())
                        .build()).toList();
    }


    public UserDTO getUserInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userRepository.findByEmail(email).map(
                user -> UserDTO.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build()
        ).orElseThrow(
                () -> new UserNotFoundException("l'utente con email " + email + " non è stato trovato")
        );
    }
}
