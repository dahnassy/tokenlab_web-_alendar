package com.voxesoftware.springboot.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(@NotBlank String name, @NotBlank String userName, @NotBlank String email, @NotBlank String password) {
	
}
