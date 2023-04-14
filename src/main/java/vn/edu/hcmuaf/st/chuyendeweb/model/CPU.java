package vn.edu.hcmuaf.st.chuyendeweb.model;

public enum CPU {
    NON("Null","Chưa phân loại"),
    I3("core i3", "Core i3"),
    I5("core i5", "Core i5"),
    I7("core i7", "Core i7"),
    I9("core i9", "Core i9"),
    XEON("xeon", "Xeon"),
    RYZEN3("ryzen", "Ryzen 5"),
    RYZEN5("ryzen", "Ryzen 5"),
    RYZEN7("ryzen", "Ryzen 7");

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
