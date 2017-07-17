package com.cjburkey.newplusplus.tile;

public class TileInstance {
	
	private IWorldTile tile;
	
	public TileInstance(IWorldTile tile) {
		this.tile = tile;
	}
	
	public IWorldTile getTile() {
		return tile;
	}
	
}