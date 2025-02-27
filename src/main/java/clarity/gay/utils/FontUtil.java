package clarity.gay.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class FontUtil {

    private FontUtil() {
    }

    public static void drawString(String str, float x, float y, int color) {
        typeToFont().drawString(str, new Vector2f(x, y), color);
    }

    public static void drawString(String str, Vector2f pos, int color) {
        drawString(str, pos.x, pos.y, color);
    }

    public static void drawCenteredString(String str, float x, float y, int color) {
        drawString(str, x - getStringWidth(str) / 2.0f, y - getFontHeight() / 2.0f, color);
    }

    public static void drawCenteredString(String str, Vector2f pos, int color) {
        drawCenteredString(str, pos.x, pos.y, color);
    }

    public static void drawStringWithShadow(String str, float x, float y, int color) {
        typeToFont().drawStringWithShadow(str, x, y, color);
    }

    public static void drawStringWithShadow(String str, Vector2f pos, int color) {
        drawStringWithShadow(str, pos.x, pos.y, color);
    }

    public static void drawCenteredStringWithShadow(String str, float x, float y, int color) {
        drawStringWithShadow(str, x - getStringWidth(str) / 2.0f, y - getFontHeight() / 2.0f, color);
    }

    public static void drawCenteredStringWithShadow(String str, Vector2f pos, int color) {
        drawCenteredStringWithShadow(str, pos.x, pos.y, color);
    }

    public static void drawScaledString(String str, float x, float y, int color, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glScalef(scale, scale, scale);
        typeToFont().drawString(str, new Vector2f(0, 0), color);
        GL11.glPopMatrix();
    }

    public static void drawScaledString(String str, Vector2f pos, int color, float scale) {
        drawScaledString(str, pos.x, pos.y, color, scale);
    }

    public static void drawScaledStringWithShadow(String str, float x, float y, int color, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glScalef(scale, scale, scale);
        typeToFont().drawStringWithShadow(str, 0, 0, color);
        GL11.glPopMatrix();
    }

    public static void drawScaledStringWithShadow(String str, Vector2f pos, int color, float scale) {
        drawScaledStringWithShadow(str, pos.x, pos.y, color, scale);
    }

    public static void drawCenteredScaledString(String str, float x, float y, int color, float scale) {
        float scaledWidth = getStringWidth(str) * scale;
        float scaledHeight = getFontHeight() * scale;
        drawScaledString(str, x - scaledWidth / 2.0f, y - scaledHeight / 2.0f, color, scale);
    }

    public static void drawCenteredScaledString(String str, Vector2f pos, int color, float scale) {
        drawCenteredScaledString(str, pos.x, pos.y, color, scale);
    }

    public static void drawCenteredScaledStringWithShadow(String str, float x, float y, int color, float scale) {
        float scaledWidth = getStringWidth(str) * scale;
        float scaledHeight = getFontHeight() * scale;
        drawScaledStringWithShadow(str, x - scaledWidth / 2.0f, y - scaledHeight / 2.0f, color, scale);
    }

    public static void drawCenteredScaledStringWithShadow(String str, Vector2f pos, int color, float scale) {
        drawCenteredScaledStringWithShadow(str, pos.x, pos.y, color, scale);
    }

    public static float getStringWidth(String str) {
        return typeToFont().getStringWidth(str);
    }

    public static float getFontHeight() {
        return typeToFont().getHeight();
    }

    public static FontRenderer typeToFont() {
        FontRenderer font = Fonts.INSTANCE.getProduct();
        if (font == null) {
            throw new IllegalStateException("fontrenderrer not initititit.");
        }
        return font;
    }
}