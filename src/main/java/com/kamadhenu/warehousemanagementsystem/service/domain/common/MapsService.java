package com.kamadhenu.warehousemanagementsystem.service.domain.common;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Maps service
 */
@Service("mapsService")
@Data
@ToString
@EqualsAndHashCode
public class MapsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapsService.class);

    @Value("${google.apikey}")
    private String apiKey;

    @Autowired
    private LocationService locationService;

    /**
     * Update latitude and longitude of all locations
     */
    public void updateGeoCoding() {
        List<Location> locationList = locationService.getAll();
        for (Location location : locationList) {
            if (!location.hasGeoCodes()) {
                updateGeoCoding(location);
            }
        }
    }

    /**
     * Update latitude and longitude of a location
     *
     * @param location
     */
    public void updateGeoCoding(Location location) {
        try {
            GeoApiContext geoApiContext = new GeoApiContext.Builder()
                    .apiKey(apiKey)
                    .build();
            GeocodingResult[] geocodingResults = GeocodingApi.geocode(
                    geoApiContext,
                    location.getFriendlyName()
            ).await();
            for (GeocodingResult geocodingResult : geocodingResults) {
                Geometry geometry = geocodingResult.geometry;
                LatLng latLng = geometry.location;
                location.setLatitude(latLng.lat);
                location.setLongitude(latLng.lng);
                locationService.edit(location);
                break;
            }
        } catch (ApiException | IOException | InterruptedException e) {
            LOGGER.error("Geocoding had errors {}", e.getLocalizedMessage());
        }
    }
}

