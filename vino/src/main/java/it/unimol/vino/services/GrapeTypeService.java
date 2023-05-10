package it.unimol.vino.services;

import java.util.List;

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

    public List<GrapeType> getAll() {
        return this.grapeType.findAll();
    }

    public GrapeType get(String type) {
        return this.grapeType.findById(type)
                .orElseThrow(() -> new EntityNotFoundException("Non esiste alcun tipo d'uva " + type));
    }
    public GrapeType put(@Valid GrapeType grapeType) {
        return this.grapeType.save(grapeType);
    }

}
