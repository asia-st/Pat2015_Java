package com.patronat;

import com.codahale.metrics.health.HealthCheck;


public class TemplateHealthCheck extends HealthCheck {
    private String template;

    public TemplateHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        if (template.isEmpty()) {
            return Result.unhealthy("Wrong template.");
        }
        return Result.healthy();
    }
}