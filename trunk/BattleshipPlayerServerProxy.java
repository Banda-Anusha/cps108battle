
public class BattleshipPlayerServerProxy implements IBattleshipPlayer {

	@Override
	public boolean addMove(BattleshipMove move) {
		return false;
	}

	@Override
	public boolean addShipPlacement(BattleshipPlacement place, ShipShape shape) {
		return false;
	}

	@Override
	public BattleshipMove getMove() {
		
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BattleshipPlacement getShipPlacement(ShipShape shape) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void processMove(BattleshipMove m, CellState newState,
			ShipShape shape) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModel(IBattleshipModel ibm) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setView(IBattleshipView yourBattleView) {
		// TODO Auto-generated method stub

	}

}
