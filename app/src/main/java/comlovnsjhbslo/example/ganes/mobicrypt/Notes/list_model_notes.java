package comlovnsjhbslo.example.ganes.mobicrypt.Notes;

public class list_model_notes {
    private String heading;
    private String Content;

    public list_model_notes(String heading, String content) {
        this.heading = heading;
        this.Content = content;
    }

    public String getHeading() {
        return heading;
    }

    public String getContent() {
        return Content;
    }
}
