package gamecore.progression;

/**
 * A projectile will either be in one of the following states
 * Prelaunch - The projectile has not been launched.
 * Launched - The projectile has been launched.
 * Postlaunch - The projectile launch has concluded.
 */
public enum ProjectileProgression implements Progression {
    PRELAUNCH, LAUNCHED, POSTLAUNCH
}
