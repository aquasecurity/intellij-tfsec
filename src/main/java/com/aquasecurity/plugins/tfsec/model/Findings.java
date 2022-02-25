package com.aquasecurity.plugins.tfsec.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class Findings {

    @JsonProperty("results")
    public List<Finding> Findings;


    public List<Finding> GetBySeverity(Severity severity) {
        return Findings.stream().filter(f -> f.Severity == severity).collect(Collectors.toList());
    }

    public void setBasePath(String basePath) {
        Findings.forEach(f -> f.setBasePath(basePath));
    }
}