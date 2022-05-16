package study.Demo.javaBASE;

public class _28People {

    private String name;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public _28People(String name, String phoneNumber) {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name +
        ",  phoneNumber='" + phoneNumber   +
        '}';
    }

    @Override
    public boolean equals(Object arg0) {
        //  TODO  Auto-generated  method  stub
        _28People p = (_28People) arg0;
        return name.equals(p.name) && phoneNumber.equals(p.phoneNumber);
    }

    @Override
    public int hashCode() {
        //  TODO  Auto-generated  method  stub
        String str = name + phoneNumber;
        return str.hashCode();
    }

}
