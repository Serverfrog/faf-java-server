package com.faforever.server.config;

import com.faforever.server.integration.ChannelNames;
import com.faforever.server.integration.ClientConnectionChannelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.security.channel.SecuredChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.security.messaging.context.SecurityContextChannelInterceptor;

import java.util.concurrent.Executors;

/**
 * Creates Spring Integration channels. Bean names must match their entry in {@link
 * com.faforever.server.integration.ChannelNames ChannelNames}. Channels that aren't explicitly configured here will be
 * implicitly instantiated as {@link DirectChannel} by Spring.
 */
@Configuration
public class ChannelConfiguration {

  private static final String CHANNEL_SECURITY_INTERCEPTOR = "channelSecurityInterceptor";
  private static final String ROLE_USER = "ROLE_USER";
  private static final String ROLE_ADMIN = "ROLE_ADMIN";

  @Bean(name = ChannelNames.CLIENT_INBOUND)
  public MessageChannel clientInbound(SecurityContextChannelInterceptor securityContextChannelInterceptor,
                                      ClientConnectionChannelInterceptor clientConnectionChannelInterceptor) {
    return MessageChannels.direct()
      .interceptor(clientConnectionChannelInterceptor)
      .interceptor(securityContextChannelInterceptor)
      .get();
  }

  @Bean(name = ChannelNames.INBOUND_DISPATCH)
  public MessageChannel inboundDispatch(SecurityContextChannelInterceptor securityContextChannelInterceptor) {
    return MessageChannels
      .executor(Executors.newSingleThreadExecutor(r -> new Thread(r, "inbound-dispatch")))
      .interceptor(securityContextChannelInterceptor)
      .get();
  }

  @Bean(name = ChannelNames.CLIENT_OUTBOUND)
  public MessageChannel clientOutbound() {
    return MessageChannels
      .executor(Executors.newFixedThreadPool(4, r -> new Thread(r, "client-outbound")))
      .get();
  }

  @Bean(name = ChannelNames.JOIN_GAME_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel joinGameRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.LEGACY_AVATAR_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel avatarRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.LEGACY_ADD_FRIEND_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel addFriendRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.LEGACY_REMOVE_FRIEND_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel removeFriendRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.LEGACY_ADD_FOE_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel addFoeRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.LEGACY_REMOVE_FOE_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel removeFoeRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.HOST_GAME_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel hostGameRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.UPDATE_GAME_STATE_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel updateGameStateRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.GAME_OPTION_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel gameOptionRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.PLAYER_OPTION_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel playerOptionRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.CLEAR_SLOT_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel clearSlotRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.AI_OPTION_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel aiOptionRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.DESYNC_REPORT)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel desyncReport() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.ARMY_SCORE_REPORT)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel armyScoreReport() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.ARMY_OUTCOME_REPORT)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel armyOutcomeReport() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.GAME_MODS_REPORT)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel gameModsReport() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.GAME_MODS_COUNT_REPORT)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel gameModsCountReport() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.OPERATION_COMPLETE_REPORT)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel operationCompleteReport() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.GAME_STATISTICS_REPORT)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel gameStatisticsReport() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.ENFORCE_RATING_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel enforceRatingRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.TEAM_KILL_REPORT)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel teamKillReport() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.LEGACY_COOP_LIST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel listCoopRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.MATCH_MAKER_SEARCH_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel matchMakerSearchRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.MATCH_MAKER_CANCEL_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel matchMakerCancelRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.DISCONNECT_PEER_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel disconnectPeerRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.DISCONNECT_CLIENT_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_ADMIN)
  public SubscribableChannel disconnectClientRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.ICE_SERVERS_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel iceServersRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.ICE_MESSAGE)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel iceMessage() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.RESTORE_GAME_SESSION_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel restoreGameSessionRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.MUTUALLY_AGREED_DRAW_REQUEST)
  @SecuredChannel(interceptor = CHANNEL_SECURITY_INTERCEPTOR, sendAccess = ROLE_USER)
  public SubscribableChannel mutuallyAgreedDrawRequest() {
    return MessageChannels.direct().get();
  }

  @Bean(name = ChannelNames.CLIENT_DISCONNECTED_EVENT)
  public SubscribableChannel clientDisconnectedEvent() {
    return MessageChannels.publishSubscribe().get();
  }
}
