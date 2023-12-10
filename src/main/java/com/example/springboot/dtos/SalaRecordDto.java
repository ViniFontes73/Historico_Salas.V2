package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;

public record SalaRecordDto(@NotBlank String nome, @NotBlank String bloco) {

}
