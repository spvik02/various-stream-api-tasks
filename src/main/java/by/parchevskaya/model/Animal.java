package by.parchevskaya.model;

public class Animal {
    private int id;

    private String bread;

    private int age;

    private String origin;

    private String gender;

    public Animal() {}

    public Animal(int id, String bread, int age, String origin, String gender) {
        this.id = id;
        this.bread = bread;
        this.age = age;
        this.origin = origin;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBread() {
        return bread;
    }

    public void setBread(String bread) {
        this.bread = bread;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Animal{id=" + id + ", bread='" + bread + "', age=" + age + ", origin='" + origin + "', gender='"
            + gender +  "'}";
    }
}
