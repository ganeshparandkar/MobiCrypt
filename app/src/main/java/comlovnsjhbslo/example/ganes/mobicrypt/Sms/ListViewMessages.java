package comlovnsjhbslo.example.ganes.mobicrypt.Sms;

public class ListViewMessages {
    private  String sms_head;
    private  String sms_desc;

    public ListViewMessages(String sms_head, String sms_desc) {
        this.sms_head = sms_head;
        this.sms_desc = sms_desc;
    }

    public  String getsms_head() {
        return sms_head;
    }

    public  String getsms_desc() {
        return sms_desc;
    }

    public void setsms_head(String sms_head) {
        this.sms_head = sms_head;
    }

    public void setsms_desc(String sms_desc) {
        this.sms_desc = sms_desc;
    }

}
