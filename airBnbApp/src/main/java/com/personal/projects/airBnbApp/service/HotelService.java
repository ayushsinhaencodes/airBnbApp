package com.personal.projects.airBnbApp.service;

import com.personal.projects.airBnbApp.dto.HotelDto;
import com.personal.projects.airBnbApp.dto.HotelInfoDto;
import org.jspecify.annotations.Nullable;

public interface HotelService {
    HotelDto createHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);
    void deleteHotelById(Long id);

    void activateHotel(Long hotelId);

     HotelInfoDto getHotelInfoById(Long hotelId);
}
