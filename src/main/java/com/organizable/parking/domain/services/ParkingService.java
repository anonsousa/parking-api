package com.organizable.parking.domain.services;


import com.organizable.parking.domain.models.ParkingModel;
import com.organizable.parking.domain.repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingService {

    @Autowired
    ParkingRepository parkingRepository;

    @Transactional
    public ParkingModel save(ParkingModel parkingModel) {
        return parkingRepository.save(parkingModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingRepository.existsByApartmentAndBlock(apartment, block);
    }

    public Page<ParkingModel> findAll(Pageable pageable) {
        return parkingRepository.findAll(pageable);
    }

    public Optional<ParkingModel> findById(UUID id) {
        return parkingRepository.findById(id);
    }

    @Transactional
    public void delete(ParkingModel parkingModel){
        parkingRepository.delete(parkingModel);
    }
}


