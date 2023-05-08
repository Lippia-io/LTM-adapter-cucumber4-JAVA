package io.lippia.reporter.ltm.models;

public final class StepDTO {
    public StepDTO() {
    }

    public StepDTO(String title, String description, String base64Image, String status) {
        this.title = title;
        this.description = description;
        this.base64Image = base64Image;
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setBase64Image(String baase64Image) {
        this.base64Image = baase64Image;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    private String title;
    private String description;
    private String base64Image;
    private String status;
}
