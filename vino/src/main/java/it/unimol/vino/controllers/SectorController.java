package it.unimol.vino.controllers;

import it.unimol.vino.models.enums.SectorName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8100", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/sector")
public class SectorController {

    @GetMapping("/all")
    public ResponseEntity<List<SectorName>> getAllSectors() {
        return ResponseEntity.ok(List.of(SectorName.values()));
    }
}
