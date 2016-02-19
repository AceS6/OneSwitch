package data;

/**
 * Created by Alexis on 19/03/2015.
 */
public class Contact {

    private int id;
    private String name;
    private String number;
    private int key;

    public Contact(String name, String number, int key){
        this.id = -1;
        this.name = name;
        this.number = number;
        this.key = key;
    }

    public Contact(int id, String name, String number, int key){
        this.id = id;
        this.name = name;
        this.number = number;
        this.key = key;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
