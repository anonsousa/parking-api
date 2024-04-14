package com.organizable.parking.controller;


import com.organizable.parking.domain.dtos.ParkingDto;
import com.organizable.parking.domain.models.ParkingModel;
import com.organizable.parking.domain.services.ParkingService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking")
public class ParkingController {
    @Autowired
    ParkingService parkingService;

    @PostMapping
    public ResponseEntity<Object> saveData(@RequestBody @Valid ParkingDto parkingDto){
        if(parkingService.existsByLicensePlateCar(parkingDto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if(parkingService.existsByParkingSpotNumber(parkingDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if(parkingService.existsByApartmentAndBlock(parkingDto.getApartment(), parkingDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
        }
        var parkingModel = new ParkingModel();
        BeanUtils.copyProperties(parkingDto, parkingModel);
        parkingModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.save(parkingModel));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id")UUID id){
        Optional<ParkingModel> parkingModelOptional = parkingService.findById(id);
        if(!parkingModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id")UUID id){
        Optional<ParkingModel> parkingModelOptional = parkingService.findById(id);
        if(!parkingModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        parkingService.delete(parkingModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id")UUID id,
                                                    @RequestBody @Valid ParkingDto parkingDto){
        Optional<ParkingModel> parkingModelOptional = parkingService.findById(id);
        if (!parkingModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        var parkingModel = new ParkingModel();
        BeanUtils.copyProperties(parkingDto, parkingModel);
        parkingModel.setId(parkingModelOptional.get().getId());
        parkingModel.setRegistrationDate(parkingModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.save(parkingModel));

    }
}
