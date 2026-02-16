package flow;

import flow.entities.characters.Entity;

public interface Visitor <T extends Entity> {
    void visit(T entity);
}
