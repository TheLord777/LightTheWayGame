package lightTheWay.components.environment;

import processing.core.PVector;

public class TorchCell extends Cell{
    public TorchCell(PVector p, float width, float height) {
        super(p, width, height, CellType.WALL.getValue());
    }


}
