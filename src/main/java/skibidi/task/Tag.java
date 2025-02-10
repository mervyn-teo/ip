package skibidi.task;


public class Tag {
    private String tag;

    public Tag(String tag) {
        this.tag = tag;
    }

    public Tag() {}

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
