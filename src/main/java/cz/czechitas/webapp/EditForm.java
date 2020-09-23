package cz.czechitas.webapp;

public class EditForm {

    private String name;
    private String phone;
    private String email;

    public EditForm() {
    }

    public EditForm(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
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
