package clarity.gay.modules.utils;


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
