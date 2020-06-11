package comlovnsjhbslo.example.ganes.mobicrypt.Contacts;

public class ListViewContacts {
    private String contact_name;
    private String contact_number;

    public ListViewContacts(String contact_name, String contact_number) {
        this.contact_name = contact_name;
        this.contact_number = contact_number;
    }

    public String getContact_name() {
        return contact_name;
    }
    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }
    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
}
