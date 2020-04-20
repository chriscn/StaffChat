package github.chriscn.util;

public enum ChannelEnum {

    ALL(0, "All"),
    STAFF(1, "Staff"),
    ADMIN(2, "Admin");

    private final int id;
    private final String name;

    ChannelEnum(int id, String name) {
           this.id = id;
           this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
