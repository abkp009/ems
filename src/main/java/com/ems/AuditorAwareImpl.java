package com.ems;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
// generic class for auditing (Entity)
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Abhishek");
    }
}