package lightTheWay.components.environment;

public enum CellType {
    EMPTY(0),
    WALL(1),
    LADDER(2),
    ROPE(3),
    WATER(4),
    CHEST(5),
    ENEMYSPAWN(6),
    PLAYERSPAWN(7);

    private final int value;

    CellType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CellType fromInt(int value) {
        for (CellType type : CellType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }
}
