//**************************************************************************************************
package joda.breakout.model;
//**************************************************************************************************

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************
public class Level implements Comparable<Level> {

	//==============================================================================================
	@Getter @Setter private String name;
	@Getter private int width;
	@Getter private int height;
	private List<Block> blocks = new ArrayList<>(100);
	private List<Integer> blockXPositions = new ArrayList<>(100);
	private List<Integer> blockYPositions = new ArrayList<>(100);
	//==============================================================================================

	//==============================================================================================
	public int hashCode() {
		return Objects.hash(name);
	}
	//==============================================================================================

	//==============================================================================================
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Level other = (Level) obj;
		return Objects.equals(name, other.name);
	}
	//==============================================================================================

	//==============================================================================================
	public int compareTo(Level o) {
		return name.compareTo(o.name);
	}
	//==============================================================================================

	//==============================================================================================
	public void setSize(int w, int h) {
		
		int maxX = 0;
		for (Integer x : blockXPositions) {
			maxX = Math.max(maxX, x);
		}

		int maxY = 0;
		for (Integer y : blockYPositions) {
			maxX = Math.max(maxY, y);
		}
		
		if (w >= maxX && h >= maxY) {
			this.width = w;
			this.height = h;
		} else {
			throw new RuntimeException("Level Size Error");
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void clearBlocks() {
		blocks.clear();
		blockXPositions.clear();
		blockYPositions.clear();
	}
	//==============================================================================================
	
	//==============================================================================================
	public void addBlock(Block block, int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			blocks.add(block);
			blockXPositions.add(x);
			blockYPositions.add(y);
		} else {
			throw new RuntimeException("Level Range Error");
		}
	}
	//==============================================================================================

	//==============================================================================================
	public void deleteBlock(int x, int y) {
		for (int i=0; i<blocks.size(); i++) {
			int xpos = blockXPositions.get(i);
			int ypos = blockYPositions.get(i);
			if (xpos == x && ypos == y) {
				blocks.remove(i);
				blockXPositions.remove(i);
				blockYPositions.remove(i);
				i--;
			}
		}
	}
	//==============================================================================================
	
}
//**************************************************************************************************
