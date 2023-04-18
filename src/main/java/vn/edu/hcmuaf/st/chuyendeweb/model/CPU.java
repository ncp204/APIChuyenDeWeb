package vn.edu.hcmuaf.st.chuyendeweb.model;

public enum CPU {
    I3("core i3", "Core i3"),
    I5("core i5", "Core i5"),
    I7("core i7", "Core i7"),
    I9("core i9", "Core i9"),
    XEON("xeon", "Xeon"),
    RYZEN_3("ryzen_3", "Ryzen 3"),
    RYZEN_5("ryzen_5", "Ryzen 5"),
    RYZEN_7("ryzen_7", "Ryzen 7"),
    RYZEN_9("ryzen_9", "Ryzen 9"),
    M1("m1", "M1"),
    M2("m2", "M2");

    private String cpu;
    private String description;

    CPU(String cpu, String description) {
        this.cpu = cpu;
        this.description = description;
    }

    public String getCpu() {
        return cpu;
    }

    public String getDescription() {
        return description;
    }
}
