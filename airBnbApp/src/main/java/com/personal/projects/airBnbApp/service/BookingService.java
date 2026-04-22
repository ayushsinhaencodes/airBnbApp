package com.personal.projects.airBnbApp.service;

import com.personal.projects.airBnbApp.dto.BookingDto;
import com.personal.projects.airBnbApp.dto.BookingRequest;
import com.personal.projects.airBnbApp.dto.GuestDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface BookingService {

     BookingDto initialiseBooking(BookingRequest bookingRequest);

    @Nullable BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
