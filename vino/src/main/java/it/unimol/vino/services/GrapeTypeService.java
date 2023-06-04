package it.unimol.vino.services;

import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.exceptions.DuplicateGrapeTypeException;
import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.repository.GrapeTypeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class GrapeTypeService {

    private final GrapeTypeRepository grapeType;

    public List<GrapeTypeDTO> getAll() {
        return this.grapeType.findAll().stream().map(
                grapeType -> GrapeTypeDTO.builder()
                        .id(grapeType.getId())
                        .species(grapeType.getSpecies())
                        .color(grapeType.getColor())
                        .build()
        ).toList();
    }

    public List<GrapeTypeDTO> getAllBySpecies(String type) {
        return this.grapeType.findBySpecies(type.toUpperCase()).stream().map(
                grapeType1 -> GrapeTypeDTO.builder()
                        .id(grapeType1.getId())
                        .species(grapeType1.getSpecies())
                        .color(grapeType1.getColor())
                        .build()
        ).toList();
    }

    public GrapeTypeDTO put(@Valid GrapeType grapeType) {
        grapeType.setColor(grapeType.getColor().toUpperCase());
        grapeType.setSpecies(grapeType.getSpecies().toUpperCase());

        this.grapeType.findBySpeciesAndColor(grapeType.getSpecies(), grapeType.getColor())
                .ifPresent(existingGrapeType -> {
                    throw new DuplicateGrapeTypeException("Esiste già un tipo d'uva "
                            + grapeType.getSpecies() +
                            " di colore " + grapeType.getColor()
                    );
                });
        return GrapeTypeDTO.getFullGrapeTypeDTO(this.grapeType.save(grapeType));
    }
}
