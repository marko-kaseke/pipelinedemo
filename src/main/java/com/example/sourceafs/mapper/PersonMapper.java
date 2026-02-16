package com.example.sourceafs.mapper;

import com.example.sourceafs.dto.PersonRequestDTO;
import com.example.sourceafs.dto.PersonResponseDTO;
import com.example.sourceafs.entity.Person;

public class PersonMapper {

    // Convert entity → DTO
    public static PersonResponseDTO toDTO(Person person) {
        if (person == null) {
            return null;
        }
        return PersonResponseDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .build();
    }

    // Convert DTO → entity
    public static Person toEntity(PersonRequestDTO dto, byte[] serializedTemplate) {
        if (dto == null || serializedTemplate == null) {
            throw new IllegalArgumentException("Invalid data for mapping Person.");
        }

        return Person.builder()
                .name(dto.getName())
                .fingerprintTemplate(serializedTemplate)
                .build();
    }
}
