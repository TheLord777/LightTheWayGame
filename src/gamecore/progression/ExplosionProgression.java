package gamecore.progression;

/**
 * An explosion will either be in one of the following states
 * PreExplosion - The explosion has not yet occurred.
 * Explosion - The explosion is occurring.
 * PostExplosion - The explosion has concluded.
 */
public enum ExplosionProgression implements Progression{
    PREEXPLOSION,
    POSTEXPLOSION,
    EXPLOSION,
}
