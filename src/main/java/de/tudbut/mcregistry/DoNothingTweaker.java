package de.tudbut.mcregistry;

import net.minecraft.launchwrapper.*;

import java.io.File;
import java.util.List;

public class DoNothingTweaker implements ITweaker {

    public void acceptOptions(List<String> args, File gameDir, final File assetsDir, String profile) {}

    public void injectIntoClassLoader(LaunchClassLoader classLoader) {}

    public String getLaunchTarget() {return "";}

    public String[] getLaunchArguments() {return new String[0];}
}
