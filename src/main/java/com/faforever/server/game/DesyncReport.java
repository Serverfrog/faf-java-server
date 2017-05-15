package com.faforever.server.game;

import com.faforever.server.common.ClientMessage;

/**
 * Sent by the client whenever a desynchronization of the game state occurred.
 */
public class DesyncReport implements ClientMessage {

  public static final DesyncReport INSTANCE = new DesyncReport();

  private DesyncReport() {
    // Singleton instance
  }
}
