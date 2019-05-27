package ru.rpgmod.client.events

import net.minecraft.client.Minecraft
import net.minecraftforge.fml.client.FMLClientHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.relauncher.Side
import ru.rpgmod.client.KeyBindsRegister
import ru.rpgmod.client.gui.ChoiceClassGui
import ru.rpgmod.client.gui.InfoPanelGui
import ru.rpgmod.common.capabilities.PlayerClassProvider
import ru.rpgmod.common.main.Constants

@Mod.EventBusSubscriber(Side.CLIENT, modid = Constants.modid)
object EventHandler {
    @SubscribeEvent
    @JvmStatic fun clientTickEvent(event: TickEvent.WorldTickEvent) {
        val player = Minecraft.getMinecraft().player
        if(event.side.isServer && player == null) return
        if(player.getCapability(PlayerClassProvider.playerCap, null)!!.name != "") return
        Minecraft.getMinecraft().addScheduledTask { FMLClientHandler.instance().showGuiScreen(ChoiceClassGui()) }
    }

    @SubscribeEvent
    @JvmStatic fun onKeyPressed(event: InputEvent.KeyInputEvent) {
        if(KeyBindsRegister.openInfoPaneKey.isPressed)
            Minecraft.getMinecraft().addScheduledTask { FMLClientHandler.instance().showGuiScreen(InfoPanelGui()) }
    }
}