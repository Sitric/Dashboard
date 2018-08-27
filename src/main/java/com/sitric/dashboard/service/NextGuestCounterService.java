package com.sitric.dashboard.service;

/**
 *
 * Service class for increment guest counter value
 */

import com.sitric.dashboard.model.GuestCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class NextGuestCounterService {

    @Autowired
    private MongoOperations mongo;

    public int getNextGuestCounter(String counterName)
    {
        GuestCounter counter  = mongo.findAndModify(
            query(where("_id").is(counterName)),
            new Update().inc("counter",1),
            options().returnNew(true).upsert(true),
            GuestCounter.class);

        return counter.getCounter();
    }
}
