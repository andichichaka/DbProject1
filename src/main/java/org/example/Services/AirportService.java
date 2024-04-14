package org.example.Services;

import org.example.DTOs.AirportDTO;
import org.example.DTOs.CityDTO;
import org.example.DTOs.CountryAsRootDTO;
import org.example.DTOs.CountryDTO;
import org.example.Filter.AirportFilter;
import org.example.Model.Airport;
import org.example.Repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<AirportDTO> findAllAirports() {
        return airportRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CountryAsRootDTO> findAllFilterAirports(AirportFilter filter) {
        return airportRepository.CountryAsRoot(filter);
        /*airportRepository.findAll().stream()
                .filter(airport -> matchFilter(airport, filter))
                .map(this::convertToDTO)
                .collect(Collectors.toList());*/
    }

    /*private boolean matchFilter(Airport airport, AirportFilter filter) {
        return (filter.countryIso2Codes().isEmpty() || filter.countryIso2Codes().contains(airport.getCity().getCountry().getIso2CountryCode())) &&
                (filter.cityIds().isEmpty() || filter.cityIds().contains(airport.getCity().getId())) &&
                (filter.airportIcaoCodes().isEmpty() || filter.airportIcaoCodes().contains(airport.getIcaoCode())) &&
                (filter.airportNames().isEmpty() || filter.airportNames().contains(airport.getName()));
    }*/

    private AirportDTO convertToDTO(Airport airport) {
        CityDTO cityDTO = new CityDTO(airport.getCity().getId(), airport.getCity().getName(),
                new CountryDTO(airport.getCity().getCountry().getName(),
                        airport.getCity().getCountry().getIso2CountryCode(),
                        airport.getCity().getCountry().getIso3CountryCode()));

        return new AirportDTO(airport.getId(), airport.getName(), cityDTO, airport.getIataCode(),
                airport.getIcaoCode(), airport.getLatitude(), airport.getLongitude());
    }
}
