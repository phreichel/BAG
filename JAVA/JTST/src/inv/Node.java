package inv;

import java.util.UUID;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class Node {

    private Node parent;
    private Set<Node> children;

    public UUID uuid;
    public String ident;
    public String name;
    public String description = "";
    public boolean container = false;

    public Node() {
        this.uuid = UUID.randomUUID();
    }

    public Node(
            String ident,
            String name,
            String description,
            boolean container,
            Node parent,
            Node ... children
    ) {
        this();
        this.ident = ident;
        this.name = name;
        this.description = description;
        this.container = container;
        link(parent, this);
        if (children != null) {
            for (var child : children) {
                link(this, child);
            }
        }
    }

    public Node parent() {
        return parent;
    }

    private static void unlink(Node parent, Node child) {
        if (parent==null) return;
        if (child==null) return;
        parent.children.remove(child);
        if (parent.children.isEmpty()) {
            parent.children = null;
        }
    }

    private static void link(Node parent, Node child) {
        if (parent==null) return;
        if (child==null) return;
        if (parent.children==null) {
            parent.children = new HashSet<Node>();
        }
        parent.children.add(child);
    }

    public void parent(Node parent) {
        unlink(this.parent,this);
        this.parent = parent;
        link(this.parent,this);
    }

    public Set<Node> children() {
        if (this.children==null) return null;
        return Collections.unmodifiableSet(this.children);
    }

}
