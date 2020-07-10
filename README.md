Calm Down, Particles!
=====

[![][cf_downloads]][cf_link] ![][license]  
![][mod_version] ![][supported_mc_version]![][lts_mc_version] [![][modloader]][forge_link] [![][mixin]][mixin_link]

[cf_downloads]: http://cf.way2muchnoise.eu/full_calm-down-particles_downloads.svg
[cf_link]: https://www.curseforge.com/minecraft/mc-mods/calm-down-particles
[mod_version]: https://img.shields.io/badge/Mod%20Version-1.0-green.svg?style=flat-square
[supported_mc_version]: https://img.shields.io/badge/MC%20Version-1.16.1-darkblue.svg?style=flat-square
[lts_mc_version]: https://img.shields.io/badge/1.15.2-blue.svg?style=flat-square
[modloader]: https://img.shields.io/badge/Mod%20Loader-Forge-red.svg?style=flat-square
[forge_link]: https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.16.1.html
[mixin]: https://img.shields.io/badge/Mixin-0.8.1-green.svg?style=flat-square
[mixin_link]: https://github.com/SpongePowered/Mixin/wiki
[side]: https://img.shields.io/badge/Side-client-yellow.svg?style=flat-square
[license]: https://img.shields.io/badge/License-Public%20Domain-green.svg?style=flat-square

*This mod fixes a annoying Forge bug: "Particles sometimes 'losing' the lightmap and drawn as fullbright."*

### 🔺 Reminder

- Forge 1.16.1-32.0.59 didn't fix this issue correctly, so it still exists.
- The author is NOT reponsible for breaking the game or your saves.
- This mod may conflict with others and I don't have the duty to find it out for you.

### 📜 History

Forge allows custom particle types, therefore changing the rendering order of particles and causing particle issues. Forge tried to fix it but failed to fix the one with lighting.

I submitted the bug report and received no reply. Then I decided to fix it by myself.

### 📖 Description

All the particles will be rendered as fullbright sometimes when an entity picks up an item or an experience orb.

The mod fixes it.

### 📝 Configuration

The fix is not hacky, therefore does not need a configuration.

### 🔗 Dependencies

**Required** [MixinBootstrap](https://www.curseforge.com/minecraft/mc-mods/mixinbootstrap): For loading Mixins with Forge.

**Incompatible** [Cull Particles](https://www.curseforge.com/minecraft/mc-mods/cull-particles): Necessarily conflict.

### 🤷 FAQ

**Will it work on servers?**

No. It's a client mod. If you install it on a server, it will have no effect.

**May I give a suggestion about fixing other bugs?**

Yes. But do it by sending private messages instead of opening an issue on Github.

**May I use this in modpacks?**

Do what you want to do. This mod is dedicated to the Public Domain.

**Will you backport it to older versions?**

No. However, technical details can be found on Github, so you may do it by yourself for 1.14.4.

Versions below 1.13 do not have this issue.

### 🧱 Technical Details

This mod use Mixins to perform the patch.

#### In 🆑`net.minecraft.client.particle.ParticleManager`

**Overwrite** 🟢`renderPaticles`

```plain
/* To be converted into Injectors */

    Runnable enable = () -> {
+       lightTextureIn.enableLightmap();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.enableFog();
        RenderSystem.activeTexture(org.lwjgl.opengl.GL13.GL_TEXTURE2);
        RenderSystem.enableTexture();
        RenderSystem.activeTexture(org.lwjgl.opengl.GL13.GL_TEXTURE0);
    };
    RenderSystem.pushMatrix();
```
