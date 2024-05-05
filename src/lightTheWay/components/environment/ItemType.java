package lightTheWay.components.environment;

public enum ItemType {
    SMALL_REFILL, // 10%
    MEDIUM_REFILL, // 25%
    LARGE_REFILL, // 50%
    FULL_REFILL, // 100%
    KEY, // needed to open doors
    TORCH, // converts an empty cellt o a torch cell
    BONFIRE, // converts an empty cell to a camp component
    NO_ITEM // no item
}
