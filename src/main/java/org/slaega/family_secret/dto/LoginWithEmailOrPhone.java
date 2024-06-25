package org.slaega.family_secret.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginWithEmailOrPhone {
    @NotEmpty(message = "le nom de")
    @NotBlank(message = "Product name cannot be blank")
    @Length(min = 5, max = 50, message = "Product name must be between 5-512 characters")
    private String email;
}
