//**************************************************************************************************
package jarcanoid.model;
//**************************************************************************************************

import java.util.List;

//**************************************************************************************************
public class SimpleLevelGenerator implements LevelGenerator {

	//==============================================================================================
	public Level generate() {
		Level level = new Level();
		List<Block> blocks = level.getBlocks();
		int gridWidth = 15;
		int gridDepth = 20;
		float ySpace = 40f;
		float blockWidth = 8f;
		float blockHeight = 3f;
		level.setWidth(blockWidth * gridWidth);
		level.setHeight(ySpace + blockHeight * gridDepth);
		for (int y=0; y<gridDepth; y++) {
			for (int x=0; x<gridWidth; x++) {
				Block block = new Block();
				block.setX(x * blockWidth);
				block.setY(ySpace + y * blockHeight);
				block.setWidth(blockWidth);
				block.setHeight(blockHeight);
				block.setType(BlockType.NORMAL);
				block.setHitsToBreak(1);
				block.setBreakBonus(1f);
				blocks.add(block);
			}
		}		
		return level;
	}
	//==============================================================================================
	
}
//**************************************************************************************************
