package org.example.ecommerce.handlers;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {
}
