package com.personal.projects.airBnbApp.service;

import com.personal.projects.airBnbApp.dto.HotelDto;
import com.personal.projects.airBnbApp.dto.HotelPriceDto;
import com.personal.projects.airBnbApp.dto.HotelSearchRequest;
import com.personal.projects.airBnbApp.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
