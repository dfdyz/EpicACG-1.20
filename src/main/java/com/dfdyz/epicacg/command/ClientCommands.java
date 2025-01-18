package com.dfdyz.epicacg.command;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.particle.HSR.BlackHoleParticle;
import com.dfdyz.epicacg.config.ClientConfig;
import com.dfdyz.epicacg.registry.Effeks;
import com.dfdyz.epicacg.registry.MyAnimations;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;

@OnlyIn(Dist.CLIENT)
public class ClientCommands {

    public static void MSGClient(String str){
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.displayClientMessage(Component.nullToEmpty(str),false);
        }
    }

    protected static LiteralArgumentBuilder<CommandSourceStack> command;

    public static void Debug(){
        try{

            //MyModels.LoadOtherModel();

            Player player = Minecraft.getInstance().player;
            //LocalPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, LocalPlayerPatch.class);
            Vec3 pos = Minecraft.getInstance().player.position();
            //pos = pos.add(5,-5,5);

            BlackHoleParticle p = new BlackHoleParticle((ClientLevel) player.level(), pos, 120);
            Minecraft.getInstance().particleEngine.add(p);

            //System.out.println("AAAA");
            //AAALevel.addParticle(player.level(), false, Effeks.BLACK_HOLE.clone().position(pos));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        command = Commands.literal(EpicACG.MODID).executes(context -> {
                    LocalPlayer player = Minecraft.getInstance().player;
                    if (player != null) {
                        player.displayClientMessage(Component.nullToEmpty("Function:\nReload\nOption:\nGenShinVoice <boolean>\nDeathParticle <boolean>"),false);
                    }

                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.literal("Reload")
                        .executes(context -> {
                            ClientConfig.Load(true);
                            //BladeTrailTextureLoader.ReleaseAll();
                            //BladeTrailTextureLoader.Load();
                            MyAnimations.LoadCamAnims();
                            //Effeks.load();
                            MSGClient("[EpicACG]Reload All Config.");
                            return Command.SINGLE_SUCCESS;
                        }))
                .then(Commands.literal("Debug")
                        .executes(context -> {
                            Debug();
                            return Command.SINGLE_SUCCESS;
                        }))
                /*
                .then(Commands.literal("SwordTrail")
                        .executes(context -> {
                            MSGClient("[EpicACG]SwordTrail Render: " + ClientConfig.cfg.EnableSwordTrail);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Enabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Disabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                 */
                .then(Commands.literal("DeathParticle")
                        .executes(context -> {
                            MSGClient("[EpicACG]DeathParticle: " + ClientConfig.cfg.EnableDeathParticle);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableDeathParticle = true;
                                    ClientConfig.SaveClientCfg();
                                    MSGClient("[EpicACG]Enabled DeathParticle.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableDeathParticle = false;
                                    ClientConfig.SaveClientCfg();
                                    MSGClient("[EpicACG]Disabled DeathParticle.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("GenShinVoice")
                        .executes(context -> {
                            MSGClient("[EpicACG]GenShinVoice: " + (ClientConfig.cfg.EnableGenShinVoice ? "Enable" : "Disable"));
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableGenShinVoice = true;
                                    ClientConfig.SaveClientCfg();
                                    MSGClient("[EpicACG]Enabled GenShinVoice.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableGenShinVoice = false;
                                    ClientConfig.SaveClientCfg();
                                    MSGClient("[EpicACG]Disabled GenShinVoice.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                /*
                .then(Commands.literal("HealthBar")
                        .executes(context -> {
                            MSGClient("[EpicACG]HealthBar Render: " + ClientConfig.cfg.EnableHealthBar);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableHealthBar = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Enabled Health Bar Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableHealthBar = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Disabled Health Bar Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("RenderSelf")
                                .executes(context -> {
                                    MSGClient("[EpicACG]Rendering Health Bar for self: " + ClientConfig.cfg.RenderHealthBarSelf);
                                    return Command.SINGLE_SUCCESS;
                                }).then(Commands.literal("true")
                                        .executes(context -> {
                                            ClientConfig.cfg.RenderHealthBarSelf = true;
                                            ClientConfig.SaveCommon();
                                            MSGClient("[EpicACG]Enabled Rendering Health Bar for self.");
                                            return Command.SINGLE_SUCCESS;
                                        }))
                                .then(Commands.literal("false")
                                        .executes(context -> {
                                            ClientConfig.cfg.RenderHealthBarSelf = false;
                                            ClientConfig.SaveCommon();
                                            MSGClient("[EpicACG]Disabled Rendering Health Bar for self.");
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                )
                .then(Commands.literal("OptFineMode")
                        .executes(context -> {
                            MSGClient("[EpicACG]OptFineMode: " + ClientConfig.cfg.EnableOptFineMode);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Enabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Disabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                })))

                 */
        ;



        dispatcher.register(command);
    }


}
