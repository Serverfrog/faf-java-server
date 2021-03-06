package com.faforever.server.client;

import com.faforever.server.common.ClientMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated there is no need to request a session. The server is expected to return a session ID, however, session
 * IDs make sense in stateless protocols which the legacy protocol is not. All information sent in this request could
 * instead be sent in the login message, or in a "client features" message.
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Deprecated
public final class LegacySessionRequest implements ClientMessage {
  private static final int MAX_CACHED_VALUES = 4;

  /** Since session requests are usually the same, avoid redundant object creation but reuse instances instead. */
  private static final Map<String, LegacySessionRequest> cache;

  static {
    cache = new HashMap<>();
  }

  String userAgent;

  public static LegacySessionRequest forUserAgent(@Nullable String userAgent) {
    // Prevent memory leak by someone flooding the cache
    if (cache.size() > MAX_CACHED_VALUES) {
      cache.entrySet().iterator().remove();
    }
    return cache.computeIfAbsent(userAgent, s -> new LegacySessionRequest(userAgent));
  }
}
