package com.jolin.security.complexity;

import java.util.HashMap;
import java.util.Map;

public class BasePasswordComplexity {

    private String name="easy";
    private Boolean enable = false;

    private Map<String, BasePasswordPattern> patterns;

    public BasePasswordComplexity() {
        BasePasswordPattern medium = new BasePasswordPattern();
        medium.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,15}$");
        medium.setMessage("It can contain only uppercase and lowercase letters, digits, and 6 to 15 characters");
        BasePasswordPattern easy = new BasePasswordPattern();
        easy.setPattern("(?=.*[0-9])");
        easy.setMessage("Can only contain numbers");
        patterns = new HashMap<>();
        patterns.put("easy",easy);
        patterns.put("medium",medium);
    }

    public Map<String, BasePasswordPattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(Map<String, BasePasswordPattern> patterns) {
        this.patterns.putAll(patterns);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
