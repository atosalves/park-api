package com.atosalves.park_api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atosalves.park_api.entity.ParkingSpot;
import com.atosalves.park_api.exception.EntityNotFoundException;
import com.atosalves.park_api.exception.UniqueViolationException;
import com.atosalves.park_api.repository.ParkingSpotRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ParkingSpotService {

        private final ParkingSpotRepository parkingSpotRepository;

        @Transactional
        public ParkingSpot create(ParkingSpot parkingSpot) {
                try {
                        return parkingSpotRepository.save(parkingSpot);
                } catch (DataIntegrityViolationException e) {
                        throw new UniqueViolationException(
                                        String.format("Código '%s' já cadastrado", parkingSpot.getCode()));
                }
        }

        @Transactional(readOnly = true)
        public ParkingSpot getByCode(String code) {
                return parkingSpotRepository.findByCode(code).orElseThrow(
                                () -> new EntityNotFoundException(String.format("Vaga '%s' não encontrada", code)));
        }

}
