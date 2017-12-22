public class TypeAndIndex {
    private final ActElementType type;
    private final String index;

    TypeAndIndex(ActElementType type, String index){
        this.type = type;
        this.index = index;
    }

    public ActElementType getType() {
        return type;
    }

    public String getIndex() {
        return index;
    }
}
