package com.cjburkey.newplusplus.world;

import com.cjburkey.newplusplus.render.Mesh;
import com.cjburkey.newplusplus.tile.IWorldTile;
import com.cjburkey.newplusplus.tile.TileInstance;

public class WorldObject {
	
	public static final int levelSize = 64;
	
	private TileInstance[] world = new TileInstance[levelSize * levelSize];
	
	public Mesh getMesh() {
		
	}
	
	public void setTile(int x, int y, IWorldTile tile) {
		world[x + y * levelSize] = new TileInstance(tile);
	}
	
	public TileInstance getTileAt(int x, int y) {
		return world[x + y * levelSize];
	}
	
}