package com.faforever.server.entity;

import com.faforever.server.client.ClientConnection;
import com.faforever.server.client.ConnectionAware;
import com.faforever.server.game.PlayerGameState;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Entity
@Table(name = "login")
@Getter
@Setter
public class Player extends Login implements ConnectionAware {

  @OneToOne(mappedBy = "player", fetch = FetchType.LAZY)
  @Nullable
  private Ladder1v1Rating ladder1v1Rating;

  @OneToOne(mappedBy = "player", fetch = FetchType.LAZY)
  @Nullable
  private GlobalRating globalRating;

  @OneToOne(mappedBy = "player", fetch = FetchType.LAZY)
  private MatchMakerBanDetails matchMakerBanDetails;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "avatars",
    joinColumns = @JoinColumn(name = "idUser", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "idAvatar", referencedColumnName = "id"))
  private List<AvatarAssociation> availableAvatars = new ArrayList<>();

  @OneToMany(mappedBy = "player")
  private List<ClanMembership> clanMemberships;

  @OneToMany(mappedBy = "player")
  private List<SocialRelation> socialRelations;

  @OneToOne
  @JoinColumn(name = "id", insertable = false, updatable = false)
  private User user;

  @Transient
  private Game currentGame;

  @Transient
  private PlayerGameState gameState = PlayerGameState.NONE;

  @Transient
  private ClientConnection clientConnection;

  /** ID of players who reported that this player spoofed their data. */
  @Transient
  private Set<Integer> fraudReporterIds = new HashSet<>();

  /**
   * The future that will be completed as soon as the player's game entered {@link GameState#OPEN}. A player's game may
   * never start if it crashes or the player disconnects.
   */
  @Transient
  private CompletableFuture<Game> gameFuture;

  /** The player's rating for the game he joined, at the time he joined. */
  @Transient
  private Rating ratingWithinCurrentGame;

  public Clan getClan() {
    if (getClanMemberships() != null && getClanMemberships().size() == 1) {
      return getClanMemberships().get(0).getClan();
    }
    return null;
  }

  public void setGameState(PlayerGameState gameState) {
    PlayerGameState.verifyTransition(this.gameState, gameState);
    this.gameState = gameState;
  }

  @Override
  public String toString() {
    return "Player(" + getId() + ", " + getLogin() + ")";
  }
}
