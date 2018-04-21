package com.faforever.server.integration.v2.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Message sent from the client to the server informing it about a chat message that has been sent by a player in the
 * game lobby.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
class GameChatMessageClientMessage extends V2ClientMessage {
  /** The chat message that has been sent. */
  private String message;
}