import java.util.List;

abstract public class AbstractNode implements INode {
    protected List<INode> children;

    @Override
    abstract public void addChild(INode node);
}
