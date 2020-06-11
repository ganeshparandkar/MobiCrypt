package comlovnsjhbslo.example.ganes.mobicrypt.model;

public class notes {
    private String notes_heading;
    private String notes_content;

    public notes(String notes_heading, String notes_content) {
        this.notes_heading = notes_heading;
        this.notes_content = notes_content;
    }

    public String getNotes_heading() {
        return notes_heading;
    }

    public void setNotes_heading(String notes_heading) {
        this.notes_heading = notes_heading;
    }

    public String getNotes_content() {
        return notes_content;
    }

    public void setNotes_content(String notes_content) {
        this.notes_content = notes_content;
    }
}
