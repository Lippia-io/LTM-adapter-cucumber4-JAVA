package io.lippia.reporter.ltm.models;

public final class RunDTO {
    public RunDTO() {
    }

    public void setRunName(String runName) {
        this.runName = runName;
    }

    public String getRunName() {
        return runName;
    }

    public void setProject(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    private String runName;
    private String projectCode;
}
