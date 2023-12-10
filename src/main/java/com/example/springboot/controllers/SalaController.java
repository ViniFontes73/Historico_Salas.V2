package com.example.springboot.controllers;

import com.example.springboot.dtos.SalaRecordDto;
import com.example.springboot.models.SalaModel;
import com.example.springboot.repositories.SalaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class SalaController {

    @Autowired
    SalaRepository salaRepository;

    @PostMapping("/salas")
    public ResponseEntity<SalaModel> saveSala(@RequestBody @Valid SalaRecordDto salaRecordDto) {
        var salaModel = new SalaModel();
        BeanUtils.copyProperties(salaRecordDto, salaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(salaRepository.save(salaModel));
    }

    @GetMapping("/salas")
    public ResponseEntity<List<SalaModel>> getAllSalas() {
        return ResponseEntity.status(HttpStatus.OK).body(salaRepository.findAll());
    }

    @GetMapping("/salas/{id}")
    public ResponseEntity<Object> getOneSala(@PathVariable(value = "id") UUID id) {
        Optional<SalaModel> salaO = salaRepository.findById(id);
        if (salaO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(salaO.get());
    }

    @PutMapping("/salas/{id}")    // buscar id para att
    public ResponseEntity<Object> updateSala(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid SalaRecordDto salaRecordDto) {  // Ele recebe o pathvariable  e pode att nome e bloco por isso requestBody e o recordDto
        Optional<SalaModel> salaO = salaRepository.findById(id);
        if (salaO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala não encontrada.");
        }
        var salaModel = salaO.get();
        BeanUtils.copyProperties(salaRecordDto, salaModel);
        return ResponseEntity.status(HttpStatus.OK).body(salaRepository.save(salaModel));
    }

    @DeleteMapping("/salas/{id}") // mesma coisa que o att e getOne
    public ResponseEntity<Object> deleteSala(@PathVariable(value = "id") UUID id) {
        Optional<SalaModel> salaO = salaRepository.findById(id);
        if (salaO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala não encontrada.");
        }
        salaRepository.delete(salaO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Sala deleta com sucesso!");
    }



}


