package com.example.ingredieat.dto;

import lombok.Data;

import java.util.List;

@Data
public class StepDto {

    private List<String> ingredients;
    private List<String> equipments;
    private String instruction;
}
