package com.personal.projects.airBnbApp.service;

import com.personal.projects.airBnbApp.dto.BookingDto;
import com.personal.projects.airBnbApp.dto.BookingRequest;
import com.personal.projects.airBnbApp.dto.GuestDto;
import com.personal.projects.airBnbApp.dto.HotelReportDto;
import com.personal.projects.airBnbApp.entity.enums.BookingStatus;
import com.stripe.model.Event;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

     BookingDto initialiseBooking(BookingRequest bookingRequest);

    @Nullable BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

    String initiatePayments(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);
    BookingStatus getBookingStatus(Long bookingId);

     List<BookingDto> getAllBookingsByHotelId(Long hotelId);

     HotelReportDto getHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);

     List<BookingDto> getMyBookings();
}
