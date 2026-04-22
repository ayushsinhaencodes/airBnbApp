package com.personal.projects.airBnbApp.controller;

import com.personal.projects.airBnbApp.dto.HotelDto;
import com.personal.projects.airBnbApp.dto.HotelInfoDto;
import com.personal.projects.airBnbApp.dto.HotelPriceDto;
import com.personal.projects.airBnbApp.dto.HotelSearchRequest;
import com.personal.projects.airBnbApp.entity.Hotel;
import com.personal.projects.airBnbApp.service.HotelService;
import com.personal.projects.airBnbApp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
       var page=  inventoryService.searchHotels(hotelSearchRequest);
       return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
