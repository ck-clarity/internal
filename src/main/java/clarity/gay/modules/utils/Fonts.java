package clarity.gay.modules.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public enum Fonts {
    INSTANCE;

    public FontRenderer getProduct() {
        return product;
    }

    public void setProduct(FontRenderer apple) {
        this.product = apple;
    }

    private FontRenderer product;

    private net.minecraft.client.gui.FontRenderer bit;

    public void setup() {
        product = new FontRenderer(fontFromTTF(new ResourceLocation("clarity/fonts/sans.ttf"),
                18, Font.PLAIN), true, true);
        loadBitFont();
    }

    public void loadBitFont() {
        bit = new net.minecraft.client.gui.FontRenderer(Minecraft.getMinecraft().gameSettings,
                new ResourceLocation("clarity/fonts/Bit.png"),
                Minecraft.getMinecraft().renderEngine, false);

        if (Minecraft.getMinecraft().gameSettings.language != null) {
            bit.setUnicodeFlag(Minecraft.getMinecraft().isUnicode());
            bit.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
        }

        Minecraft.getMinecraft().mcResourceManager.registerReloadListener(bit);
    }

    public static Font fontFromTTF(ResourceLocation fontLocation, float fontSize, int fontType) {
        Font output = null;
        try {
            output = Font.createFont(fontType, Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}