package de.tudbut.mcregistry;

import de.tudbut.tools.Registry;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

public class MCRegistry {
    public static final Registry GlobalRegistry;
    static {
        try {
            GlobalRegistry = new Registry("Registry.tcnm");
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Registry registerMod(String modid) throws IllegalAccessException {
        return new Registry(GlobalRegistry.register(modid));
    }

    public static void unregisterMod(String modid, Registry modRegistry) throws IllegalStateException, IllegalAccessException {
        GlobalRegistry.unregister(modid, modRegistry.leak());
    }
}
