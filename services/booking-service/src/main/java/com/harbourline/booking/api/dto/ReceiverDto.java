package com.harbourline.booking.api.dto;

import jakarta.validation.constraints.*;

/** The consignee as the portal sends it ("receiver"). */
public record ReceiverDto(@NotBlank String name, String company, String phone, @Email String email,
                          @NotBlank String address, @NotBlank String city, @NotBlank String postcode,
                          @Size(min = 2, max = 2) String country, String eori) {
}
