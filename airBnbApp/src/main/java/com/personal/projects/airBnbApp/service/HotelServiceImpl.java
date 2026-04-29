package com.personal.projects.airBnbApp.service;

import com.personal.projects.airBnbApp.dto.HotelDto;
import com.personal.projects.airBnbApp.dto.HotelInfoDto;
import com.personal.projects.airBnbApp.dto.RoomDto;
import com.personal.projects.airBnbApp.entity.Hotel;
import com.personal.projects.airBnbApp.entity.Room;
import com.personal.projects.airBnbApp.entity.User;
import com.personal.projects.airBnbApp.exception.ResourceNotFoundException;
import com.personal.projects.airBnbApp.exception.UnauthorizedException;
import com.personal.projects.airBnbApp.repository.HotelRepository;
import com.personal.projects.airBnbApp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hotel.setOwner(user);
        hotel = hotelRepository.save(hotel);
        log.info("Creating a new hotel with id: {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with id: {}", id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user doesnot own this hotel with id: " + id);
        }
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user doesnot own this hotel with id: " + id);
        }
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user does not own this hotel with id: " + id);
        }

        for (Room room : hotel.getRooms()) {
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!user.equals(hotel.getOwner())) {
            throw new UnauthorizedException("This user does not own this hotel with id: " +hotelId);
        }
        hotel.setActive(true);

        //assuming only do it once
        for (Room room : hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }

    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
        List<RoomDto> rooms = hotel.getRooms().
                stream().map((element) -> modelMapper.map(element, RoomDto.class)).toList();
        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }
}

