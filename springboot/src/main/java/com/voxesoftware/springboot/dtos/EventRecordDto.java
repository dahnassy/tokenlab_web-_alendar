package com.voxesoftware.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventRecordDto(@NotNull Integer idUser, @NotBlank String name, @NotBlank String startDate, @NotBlank String endDate, @NotBlank String description, String imageId) {

}
