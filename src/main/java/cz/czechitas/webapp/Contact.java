package cz.czechitas.webapp;

public class Contact {


    private Long number ; //    Číslo
    private String name; //    Jméno
    private String phone; //    Telefon
    private String email; //    Email

    public Contact() {
    }

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Contact(Long number, String name, String phone, String email) {
        this.number = number;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Long getNumber() {
        return number;
    }



    public String getName() {
        return name;
    }

    public void setName(String newValue) {
        name = newValue;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String newValue) {
        phone = newValue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newValue) {
        email = newValue;
    }
}
