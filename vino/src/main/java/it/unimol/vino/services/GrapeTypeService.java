package it.unimol.vino.services;

import java.util.List;

import it.unimol.vino.dto.GrapeTypeDTO;
import it.unimol.vino.exceptions.DuplicateGrapeTypeException;
import it.unimol.vino.exceptions.GrapeTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import it.unimol.vino.models.entity.GrapeType;
import it.unimol.vino.repository.GrapeTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class GrapeTypeService {

    private final GrapeTypeRepository grapeType;

    public List<GrapeTypeDTO> getAll() {
        return this.grapeType.findAll().stream().map(
                grapeType -> GrapeTypeDTO.builder()
                        .species(grapeType.getSpecies())
                        .color(grapeType.getColor())
                        .build()
        ).toList();
    }

    public GrapeType get(String type) {
        return this.grapeType.findBySpecies(type)
                .orElseThrow(() -> new EntityNotFoundException("Non esiste alcun tipo d'uva " + type));
    }

    public GrapeTypeDTO put(@Valid GrapeType grapeType) {
        this.grapeType.findBySpeciesAndColor(grapeType.getSpecies(), grapeType.getColor())
                .ifPresent(existingGrapeType -> {
                    throw new DuplicateGrapeTypeException("Esiste già un tipo d'uva " + grapeType.getSpecies() + " di colore " + grapeType.getColor());
                });
        return GrapeTypeDTO.getFullGrapeTypeDTO(this.grapeType.save(grapeType));
    }
}
