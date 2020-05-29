Calm Down, Particles
=====

[Download me on CurseForge](https://www.curseforge.com/minecraft/mc-mods/calm-down-particles)

This is a mod which fixes MinecraftForge/MinecraftForge#6706 "Particle rendered as full-bright for a few frames on item/experience pickup" for MC1.15.2.

Note that the weird-looking logo is intentional.

Relations
-----

**Requires** [MixinBootstrap](https://www.curseforge.com/minecraft/mc-mods/mixinbootstrap): For loading Mixins.

Effect
-----

![](logo-standard.png)

In the logo, the particles are highlighted. This shows the look of "flickering". The mod fixes this.

Side Effects
-----

The mod has a little performance impact (it's necessary). The changes to performance should not even be inspectable.

Issue Analysis
-----

If you download the deobf-ed sources of Forge-patched MC, you can see a fix for MC-168672, but it did not fix the bug completely(likely a stupid mistake). MC-168672 is the root cause of the "flickering particles" issue, however the issue is mostly invisible without Forge.

```
net.minecraft.client.particle.ParticleManager#renderParticles

  if (iparticlerendertype == IParticleRenderType.NO_RENDER) continue;
  enable.run(); //Forge: MC-168672 Make sure all render types have the correct GL state.
+ lightTextureIn.enableLightmap(); //Forge is right, but made a mistake. I'll fix it.
  Iterable<Particle> iterable = this.byType.get(iparticlerendertype);
```

For more information go to MinecraftForge/MinecraftForge#6706.

Incompatibilities
-----

**Early Loading Crash** [Cull Particles](https://www.curseforge.com/minecraft/mc-mods/cull-particles): Cull Particles patches `net.minecraft.client.particle.ParticleManager#renderParticles` too, causing Mixins conflict.

Feel free to report incompatibilities to the [Issue Tracker](https://github.com/yezhiyi9670/calm-down-particles/issues) if you found others.

Plans
-----

New features will not be added to the mod in order to keep the name descriptive. Please use [Github Issue Tracker](http://github.com/yezhiyi9670/calm-down-particles/issues) to report bugs. DO NOT report bugs using CurseForge comment as comments may not be responsed.

The mod WILL NOT be backported to MC1.14.4 and earlier. MC1.12.2 and earlier do not have the issue.
