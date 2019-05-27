package ru.rpgmod.utils

import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11

object DrawUtils {
    private var zLevel = 0.0

    fun drawTexturedModalRect(x: Int, y: Int, textureX: Int, textureY: Int, width: Int, height: Int) {
        val tess = Tessellator.getInstance()
        val buf = tess.buffer
        buf.begin(7, DefaultVertexFormats.POSITION_TEX)
        buf.pos(x.toDouble(), (y + height).toDouble(), zLevel).tex(textureX * 0.00390625, (textureY + height) * 0.00390625).endVertex()
        buf.pos((x + width).toDouble(), (y + height).toDouble(), zLevel).tex((textureX + width) * 0.00390625, (textureY + height) * 0.00390625).endVertex()
        buf.pos((x + width).toDouble(), y.toDouble(), zLevel).tex((textureX + width) * 0.00390625, textureY * 0.00390625).endVertex()
        buf.pos(x.toDouble(), y.toDouble(), zLevel).tex(textureX * 0.00390625, textureY * 0.00390625).endVertex()
        tess.draw()
    }

    fun drawColoredRect(x: Int, y: Int, width: Int, height: Int, color: Int) {
        val r = color shr 16 and 0xff
        val g = color shr 8 and 0xff
        val b = color and 0xff
        val a = 255

        val tess = Tessellator.getInstance()
        val buf = tess.buffer
        GL11.glPushMatrix()
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
        buf.pos(x.toDouble(), y.toDouble(), zLevel).color(r, g, b, a).endVertex()
        buf.pos(x.toDouble(), (y + height).toDouble(), zLevel).color(r, g, b, a).endVertex()
        buf.pos((x + width).toDouble(), (y + height).toDouble(), zLevel).color(r, g, b, a).endVertex()
        buf.pos((x + width).toDouble(), y.toDouble(), zLevel).color(r, g, b, a).endVertex()
        tess.draw()
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glPopMatrix()
    }
}