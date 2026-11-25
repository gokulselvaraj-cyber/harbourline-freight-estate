package com.harbourline.booking.config;

import org.springframework.context.annotation.Configuration;

/**
 * Authentication is done at the nginx edge (portal session cookie) and by network policy for internal
 * callers. The service itself trusts its callers.
 */
@Configuration
public class SecurityConfig {
}
