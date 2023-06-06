package it.unimol.vino.controllers;

import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.utils.Logger;
import it.unimol.vino.utils.Network;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/sector")
public class SectorController {

    @GetMapping
    public ResponseEntity<List<SectorName>> getAllSectors(HttpServletRequest servletRequest) {
        Logger.getLogger().info(Network.getClientIp(servletRequest) + " is requesting all sectors");
        return ResponseEntity.ok(List.of(SectorName.values()));
    }
}
