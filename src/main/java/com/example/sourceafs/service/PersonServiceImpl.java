package com.example.sourceafs.service;

import com.example.sourceafs.dto.MatchResponseDTO;
import com.example.sourceafs.dto.PersonRequestDTO;
import com.example.sourceafs.dto.PersonResponseDTO;
import com.example.sourceafs.entity.Person;
import com.example.sourceafs.mapper.PersonMapper;
import com.example.sourceafs.repository.PersonRepository;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    
    private static final double THRESHOLD = 40.0;

    @Override
    public PersonResponseDTO save(PersonRequestDTO request) {
        // Decode Base64 image
        byte[] imageBytes = decodeFingerprint(request.getFingerprintBase64());

        // Convert to FingerprintImage with DPI
        FingerprintImage image = new FingerprintImage(imageBytes, new FingerprintImageOptions().dpi(500));

        // Extract template
        FingerprintTemplate template = new FingerprintTemplate(image);

        // Serialize template to store in DB
        byte[] serialized = template.toByteArray();

        // Map to entity
        Person person = Person.builder()
                .name(request.getName())  // Set person's name
                .fingerprintTemplate(serialized)  // Set the fingerprint template
                .build();

        // Save
        repository.save(person);

        return PersonMapper.toDTO(person);
    }

    @Override
    public MatchResponseDTO identify(String probeBase64) {
        // Decode probe fingerprint
        byte[] probeImageBytes = decodeFingerprint(probeBase64);

        /*FingerprintTemplate probeTemplate = new FingerprintTemplate(
                new FingerprintImage(probeImageBytes, new FingerprintImageOptions().dpi(500))
        );*/

        // Create FingerprintImage from probe bytes
        FingerprintImage probeImage = new FingerprintImage(probeImageBytes, new FingerprintImageOptions().dpi(500));

        // Create FingerprintTemplate from image
        FingerprintTemplate probeTemplate = new FingerprintTemplate(probeImage);

        FingerprintMatcher matcher = new FingerprintMatcher(probeTemplate);

        List<Person> persons = repository.findAll();

        Person bestMatch = null;
        double maxSimilarity = Double.NEGATIVE_INFINITY;

        // 1:N matching
        for (Person person : persons) {
            FingerprintTemplate candidateTemplate = new FingerprintTemplate(person.getFingerprintTemplate());

            double similarity = matcher.match(candidateTemplate);

            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                bestMatch = person;
            }
        }

        // Return match response
        if (maxSimilarity >= THRESHOLD && bestMatch != null) {
            return MatchResponseDTO.builder()
                    .match(true)  // Found a match
                    .matchedPersonName(bestMatch.getName())  // Name of the matched person
                    .similarity(maxSimilarity)  // Similarity score of the match
                    .build();
        }

        // If no match is found, return a match response with 'false'
        return MatchResponseDTO.builder()
                .match(false)  // No match found
                .similarity(maxSimilarity)  // Similarity score (always negative if no match)
                .build();
    }

    /**
     * Helper method to decode a Base64-encoded fingerprint image.
     *
     * @param fingerprintBase64 Base64 string representing the fingerprint image.
     * @return The decoded byte array of the fingerprint image.
     * @throws RuntimeException If the Base64 decoding fails.
     */
    private byte[] decodeFingerprint(String fingerprintBase64) {
        try {
            if (fingerprintBase64 == null || fingerprintBase64.isEmpty()) {
                throw new IllegalArgumentException("Fingerprint image data is empty or invalid.");
            }
            return Base64.getDecoder().decode(fingerprintBase64);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to decode fingerprint image: " + e.getMessage());
        }
    }
}
