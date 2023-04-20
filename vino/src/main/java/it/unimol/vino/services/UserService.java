package it.unimol.vino.services;


import it.unimol.vino.exceptions.SectorNotFoundException;
import it.unimol.vino.exceptions.UserNotFoundException;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.models.request.UpdatePermissionsRequest;
import it.unimol.vino.models.response.UpdatePermissionResponse;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;

    @Transactional
    public UpdatePermissionResponse updatePermissions(UpdatePermissionsRequest updatePermissionsRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with email " + email + " not found")
        );
        updatePermissionsRequest.getPermissions().forEach((sectorName, permissions) -> {

            Sector sector = this.sectorRepository.findSectorBySectorName(sectorName).orElseThrow(
                    () -> new SectorNotFoundException(
                            "Sector with name " + sectorName + " does not belong to "
                                    + Arrays.toString(SectorName.values())
                    )
            );
            user.updatePermission(sector, permissions);
        });
        this.userRepository.save(user);
        return new UpdatePermissionResponse("Permissions updated successfully!");
    }
}
