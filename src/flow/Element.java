package flow;

import flow.entities.characters.Entity;

public interface Element <T extends Entity>{
    void accept(Visitor<T> visitor);
}
