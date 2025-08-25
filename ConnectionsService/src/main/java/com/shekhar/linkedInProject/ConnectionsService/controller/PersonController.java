package com.shekhar.linkedInProject.ConnectionsService.controller;

import com.shekhar.linkedInProject.ConnectionsService.entity.Person;
import com.shekhar.linkedInProject.ConnectionsService.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PersonController {
    private final PersonService personService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> firstDegreeConnectionsOfUser(@PathVariable Long userId){
        List<Person> persons = personService.firstDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(persons);
    }
}
