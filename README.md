# MCRegistry

MCRegistry aims to be a place where mod configuration can be stored, whether it be cross-mod or 
private. Mods can create public datastores and private ones only they have access to. Attempting to access
an aleady registered mod's data results in an IllegalAccessException.

## Applications

- Easily storing user configs
- Sharing data between mods (e.g. in modpacks)
- Sharing data between mod stages (e.g. between a loader and a loadee when ClassLoader difficulties or
  obfuscation stop this from being done regularly)

## How to use it

### As a user

- Just download it and put it in your mods folder. Mods which require it will automatically be able to use it.

### As a developer

- Add it as a runtime dependency (DO NOT add it to your final jar! It must be separate from any other mods.)
- Add `MCRegistry.registerMod("YOUR MOD ID")` to your code. You get back a Registry.
- You can now register modules or configuration items into that registry. Keep in mind that you can
  NOT use `registry.register("*").set("foo", "bar");` because the registry also keeps values private => Either
  unregister when you're done or save the TCN for later.
- That's it! Loading from file, saving to it, etc, are all taken care of automatically.

You can also serialize things into it using ConfigSaverTCN2's write and read methods. Use the @Save annotation
to control what's saved.
