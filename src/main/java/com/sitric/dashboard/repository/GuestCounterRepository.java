package com.sitric.dashboard.repository;

/**
 * GuestCounterRepository as MongoRepository for GuestCounter
 */

import com.sitric.dashboard.model.GuestCounter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GuestCounterRepository extends MongoRepository<GuestCounter, Long> {
}
