package comlovnsjhbslo.example.ganes.mobicrypt.model;

public class User {
    private String Name;
    private String Password;
    private String phone_number;

    //private String Notes;

    public User() {

    }

    public User(String name, String password, String phonenumber) {
        Name = name;
        Password = password;
        this.phone_number = phonenumber;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phone_number = phonenumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) { this.Name = name; }       // added this. by shriyash

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {this.Password = password;   } // added this. by shriyash
}