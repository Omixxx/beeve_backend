package it.unimol.vino.services;


import it.unimol.vino.dto.PermissionDTO;
import it.unimol.vino.exceptions.SectorNotFoundException;
import it.unimol.vino.exceptions.UserNotFoundException;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserSectorPermission;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;

    @Transactional
    public UpdatePermissionResponse updatePermissions(UpdatePermissionsRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("l'utente con email " + email + " non è stato trovato")
        );
        request.getPermissions().forEach((sectorName, permissions) -> {

            Sector sector = this.sectorRepository.findSectorBySectorName(sectorName).orElseThrow(
                    () -> new SectorNotFoundException("Il settore con il nome "
                            + sectorName
                            + " non appartiene alla lista dei settori: "
                            + Arrays.toString(SectorName.values()))
            );
            user.updatePermission(sector, permissions);
        });
        this.userRepository.save(user);
        return new UpdatePermissionResponse("Permessi aggiornati con successo!");
    }

    public List<PermissionDTO> getPermissions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("l'utente con email " + email + " non è stato trovato")
        );
        List<UserSectorPermission> userSectorPermission = user.getPermissions();
        List<PermissionDTO> permissions = new ArrayList<>();
        userSectorPermission.forEach(userSectorPermission1 -> {
            permissions.add(PermissionDTO.builder()
                    .sector(userSectorPermission1.getSector())
                    .canRead(userSectorPermission1.getCanRead())
                    .canWrite(userSectorPermission1.getCanWrite())
                    .canDelete(userSectorPermission1.getCanDelete())
                    .canUpdate(userSectorPermission1.getCanUpdate())
                    .build());
        });
        return permissions;
    }

}
