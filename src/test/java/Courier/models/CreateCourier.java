package Courier.models;

public class CreateCourier extends Courier {
    private String firstName;

    public CreateCourier(String login, String password, String firstName) {
        super(login, password);
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
