package io.lippia.reporter.ltm.models.run.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataDTO {
    public DataDTO() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @JsonProperty("id")
    private String id;
}
