package org.thefruitbox.fbtribes.utilities;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.math.BlockVector3;

public class VectorUtilities {

	public static BlockVector3 locationToBV(Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		BlockVector3 vector = BlockVector3.at(x, y, z);
		
		return vector;
	}
	
	public static BlockVector3 getBlockVector(Block b) {
		Location loc = b.getLocation();
		int blockX = loc.getBlockX();
		int blockY = loc.getBlockY();
		int blockZ = loc.getBlockZ();
		BlockVector3 position = BlockVector3.at(blockX, blockY, blockZ);
		
		return position;
	}
	
	public static BlockVector3 getPlayerVector(Player p) {
		Location loc = p.getLocation();
		int blockX = loc.getBlockX();
		int blockY = loc.getBlockY();
		int blockZ = loc.getBlockZ();
		BlockVector3 position = BlockVector3.at(blockX, blockY, blockZ);
		
		return position;
	}
	
}
