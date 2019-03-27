package com.cjburkey.xerxesplus.player;

import com.cjburkey.xerxesplus.XerxesPlus;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class FakePlayerHandler {

    private static FakePlayer player;

    public static FakePlayer getFakePlayer() {
        if (player == null) {
            player = FakePlayerFactory.get(DimensionManager.getWorld(0), new GameProfile(UUID.randomUUID(), "WowbaggerTheInfinitelyProlonged"));
            XerxesPlus.logger.info("Created fake player: " + player.getUniqueID() + " as " + player.getName());
        }
        return player;
    }

}
