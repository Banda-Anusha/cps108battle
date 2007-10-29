import java.io.Serializable;

/**
 * This enumerated type encapsulates all the possible states of a cell on a Battleship board from the point of view of a particular Player.
 * From the point of view of one Player, a cell can either be:
 * INVALID - Not on the board
 * HIT - Already tried, hit a ship, not yet sunk
 * MISS - Already tried, missed
 * SHIP - On your side, you have placed a ship here
 * NOSHIP - On your side, this cell is empty (no ship here)
 * UNKNOWN - Haven't tried this cell yet this game
 * @author mrj10
 *
 */
public enum CellState implements Serializable {
INVALID,
HIT,
MISS,
SHIP,
NOSHIP,
SUNK,
UNKNOWN;
}
