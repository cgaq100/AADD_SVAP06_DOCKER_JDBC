package es.ciudadescolar.Utils;

public class Actor {

    // valores
    private int id;
    private String firstName;
    private String lastName;
    

    // getters y setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}



    // constructor
    public Actor(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }



    // to String
    @Override
    public String toString() {
        return "Actor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }



}
