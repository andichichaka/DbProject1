package org.example.Repositories;

import org.example.DTOs.CountryAsRootDTO;
import org.example.Filter.AirportFilter;
import org.example.Model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long>, JpaSpecificationExecutor<Airport> {
    @Query(value = "SELECT c.name AS countryName, c.iso2_country_code AS iso2CountryCode, c.iso3_country_code AS iso3CountryCode, " +
            "ci.id AS cityId, ci.name AS cityName, " +
            "a.id AS airportId, a.name AS airportName, a.iata_code AS iataCode, a.icao_code AS icaoCode, " +
            "a.latitude AS latitude, a.longitude AS longitude " +
            "FROM Country c " +
            "JOIN City ci ON c.id = ci.country_id " +
            "LEFT JOIN Airport a ON ci.id = a.city_id " +
            "WHERE (:#{#airportFilter.countryIso2Codes} IS NULL OR c.iso2_country_code IN :#{#airportFilter.countryIso2Codes}) " +
            "AND (:#{#airportFilter.cityIds} IS NULL OR ci.id IN :#{#airportFilter.cityIds}) " +
            "AND (:#{#airportFilter.airportIcaoCodes} IS NULL OR a.icao_code IN :#{#airportFilter.airportIcaoCodes}) " +
            "AND (:#{#airportFilter.airportNames} IS NULL OR a.name IN :#{#airportFilter.airportNames})",
            nativeQuery = true)
    List<CountryAsRootDTO> CountryAsRoot(@Param("airportFilter") AirportFilter airportFilter);
}