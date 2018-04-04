
package com.rath.rathbot.disc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class holds a user's entire infraction history.
 * 
 * @author Tim Backus tbackus127@gmail.com
 */
public class InfractionData implements Serializable {
  
  /** Default serial version UID. */
  private static final long serialVersionUID = 1L;
  
  /** How many times the user has been warned. */
  private int warnCount;
  
  /** How many times the user has been muted. */
  private int muteCount;
  
  /** How many times the user has been kicked. */
  private int kickCount;
  
  /** How many times the user has been banned. */
  private int banCount;
  
  /** How long the user is muted for. */
  private int muteDuration;
  
  /** If the user is currently muted. */
  private boolean isMuted;
  
  /** If the user is currently banned. */
  private boolean isBanned;
  
  /** The user's infraction history, with the most recent entry being index 0 of the list. */
  private final ArrayList<InfractionEntry> history;
  
  /**
   * Default constructor.
   */
  public InfractionData() {
    this.warnCount = 0;
    this.muteCount = 0;
    this.kickCount = 0;
    this.banCount = 0;
    this.muteDuration = 0;
    this.history = new ArrayList<InfractionEntry>();
  }
  
  /**
   * Gets how many times the user has been warned.
   * 
   * @return an int.
   */
  public final int getWarnCount() {
    return this.warnCount;
  }
  
  /**
   * Increments the user's warn count.
   * 
   * @param time the epoch time the user was warned.
   * @param reason the reason the user is being warned.
   */
  public final void warn(final long time, final String reason) {
    this.history.add(new InfractionEntry(PunishmentType.WARN, time, reason));
    this.warnCount++;
  }
  
  /**
   * Gets how many times the user has been muted.
   * 
   * @return an int.
   */
  public final int getMuteCount() {
    return this.muteCount;
  }
  
  /**
   * Gets how many times the user has been kicked.
   * 
   * @return an int.
   */
  public final int getKickCount() {
    return this.kickCount;
  }
  
  /**
   * Gets how many times the user has been banned.
   * 
   * @return an int.
   */
  public int getBanCount() {
    return this.banCount;
  }
  
  /**
   * Gets the user's infraction history.
   * 
   * @return an ArrayList of InfractionEntry's.
   */
  public final ArrayList<InfractionEntry> getHistory() {
    return this.history;
  }
  
  /**
   * If the user is muted.
   * 
   * @return a boolean.
   */
  public final boolean isMuted() {
    return this.isMuted;
  }
  
  /**
   * Sets the user's muted status.
   * 
   * @param b true if the user shall be muted; false if not.
   */
  public final void setMuted(final boolean b) {
    this.isMuted = b;
  }
  
  /**
   * Sets a user's muted status to true and increments the mute count.
   * 
   * @param issueTime the epoch time the user was muted.
   * @param muteDuration the amount of seconds the user is muted for.
   * @param reason the reason the user is being muted.
   */
  public final void mute(final long issueTime, final int muteDuration, final String reason) {
    this.history.add(new InfractionEntry(PunishmentType.MUTE, issueTime, reason));
    this.muteDuration = muteDuration;
    this.muteCount++;
    setMuted(true);
  }
  
  /**
   * Increments a user's kick count.
   * 
   * @param time the epoch time the user was kicked.
   * @param reason the reason the user is being kicked.
   */
  public final void kick(final long time, final String reason) {
    this.history.add(new InfractionEntry(PunishmentType.KICK, time, reason));
    this.kickCount++;
  }
  
  /**
   * If the user is banned.
   * 
   * @return a boolean.
   */
  public final boolean isBanned() {
    return this.isBanned;
  }
  
  /**
   * Sets the user's banned status.
   * 
   * @param b true if the user shall be banned; false if not.
   */
  public final void setBanned(final boolean b) {
    this.isBanned = b;
  }
  
  /**
   * Sets a user's banned status to true and increments the ban count.
   * 
   * @param time the epoch time the user was banned.
   * @param reason the reason the user is being banned.
   */
  public final void ban(final long time, final String reason) {
    this.history.add(new InfractionEntry(PunishmentType.BAN, time, reason));
    this.banCount++;
    setBanned(true);
  }
  
  // TODO: Javadoc this
  public final int getMuteDuration() {
    return this.muteDuration;
  }
  
}
