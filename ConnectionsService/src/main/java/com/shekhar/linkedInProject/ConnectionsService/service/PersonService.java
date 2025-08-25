package com.shekhar.linkedInProject.ConnectionsService.service;

import com.shekhar.linkedInProject.ConnectionsService.entity.Person;
import com.shekhar.linkedInProject.ConnectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> firstDegreeConnectionsOfUser(Long userId){
        log.info("Getting first degree connections of user with ID: {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }
}
