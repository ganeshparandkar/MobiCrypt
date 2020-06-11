package comlovnsjhbslo.example.ganes.mobicrypt.model;

public class Messages {
    private String message_heading;
    private String message_content;

    public Messages(String message_heading, String message_content) {
        this.message_heading = message_heading;
        this.message_content = message_content;
    }

    public String getMessage_heading(String smsMessage) {
        return message_heading;
    }

    public void setMessage_heading(String message_heading) {
        this.message_heading = message_heading;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }
}
