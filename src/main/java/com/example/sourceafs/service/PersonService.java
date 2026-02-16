package com.example.sourceafs.service;

import com.example.sourceafs.dto.MatchResponseDTO;
import com.example.sourceafs.dto.PersonRequestDTO;
import com.example.sourceafs.dto.PersonResponseDTO;

public interface PersonService {
    // Save a new person with fingerprint
    PersonResponseDTO save(PersonRequestDTO request);

    // Identify a person by probe fingerprint (1:N match)
    MatchResponseDTO identify(String probeBase64);
}
