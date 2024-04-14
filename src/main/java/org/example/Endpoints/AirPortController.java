package org.example.Endpoints;

import org.example.DTOs.AirportDTO;
import org.example.Filter.AirportFilter;
import org.example.Services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/")
public class AirPortController {

    private final AirportService airportService;

    @Autowired
    public AirPortController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("/airports")
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        List<AirportDTO> dto = airportService.findAllAirports();
        return dto.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<?>> fetchFilteredAirports(@RequestParam(defaultValue = "false") boolean countryAsRoot,
                                                                  @RequestBody AirportFilter airportFilter) {
        List<?> filteredAirports = countryAsRoot ?
                airportService.findAllFilterAirports(airportFilter) : airportService.findAllAirports();
        return filteredAirports.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(filteredAirports);
    }
}
