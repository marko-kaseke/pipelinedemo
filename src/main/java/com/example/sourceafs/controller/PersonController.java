package com.example.sourceafs.controller;

import com.example.sourceafs.dto.*;
import com.example.sourceafs.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    // Save a new person
    @PostMapping
    public PersonResponseDTO save(@RequestBody PersonRequestDTO request) {
        return service.save(request);
    }

    // Identify a person from a probe fingerprint
    @PostMapping("/identify")
    public MatchResponseDTO identify(@RequestBody IdentifyRequestDTO request) {
        return service.identify(request.getFingerprintBase64());
    }
}
