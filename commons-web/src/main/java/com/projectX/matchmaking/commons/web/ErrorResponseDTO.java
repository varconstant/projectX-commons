package com.projectX.matchmaking.commons.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Common error-response shape returned by every service's exception handlers: a stable,
 * machine-readable code the client can key off (Android maps this to a localized string) plus a
 * human-readable message for logs/debugging. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private String error;
    private String message;
}
