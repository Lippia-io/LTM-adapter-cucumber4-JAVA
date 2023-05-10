package io.lippia.reporter.ltm.models.run.response;

public class RunDTO {
    public RunDTO() {
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public DataDTO getData() {
        return data;
    }

    private DataDTO data;
}