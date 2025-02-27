package clarity.gay.modules.utils;


import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FontRenderer extends CFont {
    private final int[] colorCode = new int[32];
    protected CharData[] boldChars = new CharData[256];
    protected CharData[] italicChars = new CharData[256];
    protected CharData[] boldItalicChars = new CharData[256];

    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    public FontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        setupMinecraftColorcodes();
        setupBoldItalicIDs();
    }

    public void drawCenteredScaledStringWithShadow(String str, Vector2f pos, float scale, int color) {
        drawScaledStringWithShadow(str, new Vector2f(pos.x - ((getStringWidth(str) * scale) / 2f), pos.y - ((getHeight() * scale)) / 2f), scale, color);
    }

    public float drawString(String text, Vector2f vec2, int color) {
        return drawString(text, new Vector2f(vec2.x, vec2.y), color, false);
    }

    public void drawScaledStringWithShadow(String str, Vector2f pos, float scale, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);


        GlStateManager.scale(1, 1, 1);
        GlStateManager.popMatrix();
    }

    public float drawStringWithShadow(String text, Vector2f vec2, int color) {
        float shadowWidth = drawString(text, new Vector2f(vec2.x + 0.7f, vec2.y + 0.75f), color, true);
        return Math.max(shadowWidth, drawString(text, new Vector2f(vec2.x, vec2.y), color, false));
    }

    public float drawStringWithShadow(String text, int x, int y, int color) {
        float shadowWidth = drawString(text, new Vector2f(x + 0.7f, y + 0.75f), color, true);
        return Math.max(shadowWidth, drawString(text, new Vector2f(x, y), color, false));
    }

    public float drawStringWithShadow(String text, float x, float y, int color) {
        float shadowWidth = drawString(text, new Vector2f(x + 0.7f, y + 0.75f), color, true);
        return Math.max(shadowWidth, drawString(text, new Vector2f(x, y), color, false));
    }

    public float drawCenteredString(String text, Vector2f vec2, int color) {
        return drawString(text, new Vector2f(vec2.x - getStringWidth(text) / 2, vec2.y), color);
    }

    public float drawCenteredStringWithShadow(String text, Vector2f vec2, int color) {
        return drawString(text, new Vector2f(vec2.x - getStringWidth(text) / 2, vec2.y), color);
    }

    public float drawString(String text, Vector2f vec2, int color, boolean shadow) {
        vec2.x -= 1;
        vec2.y += 1;

        if (text == null) {
            return 0.0F;
        }

        if (color == 553648127) {
            color = 16777215;
        }

        if ((color & 0xFC000000) == 0) {
            color |= -16777216;
        }

        if (shadow) {
            color = (color & 0xFCFCFC) >> 2 | color & new Color(20, 20, 20, 200).getRGB();
        }

        CharData[] currentData = this.charData;
        float alpha = (color >> 24 & 0xFF) / 255.0F;

        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;

        vec2.x *= 2.0D;
        vec2.y = (float) ((vec2.y - 3.0D) * 2.0D);

        GL11.glPushMatrix();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
        int size = text.length();
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(tex.getGlTextureId());

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGlTextureId());

        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);

            if (String.valueOf(character).equals("§")) {
                int colorIndex = 21;

                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(tex.getGlTextureId());
                    // GL11.glfunc_179144_i(GL11.GL_TEXTURE_2D,
                    // tex.getGlTextureId());
                    currentData = this.charData;

                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }

                    if (shadow) {
                        colorIndex += 16;
                    }

                    int colorcode = this.colorCode[colorIndex];
                    GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, alpha);
                } else if (colorIndex == 17) {
                    bold = true;

                    if (italic) {
                        GlStateManager.bindTexture(texItalicBold.getGlTextureId());
                        // GL11.glfunc_179144_i(GL11.GL_TEXTURE_2D,
                        // texItalicBold.getGlTextureId());
                        currentData = this.boldItalicChars;
                    } else {
                        GlStateManager.bindTexture(texBold.getGlTextureId());
                        // GL11.glfunc_179144_i(GL11.GL_TEXTURE_2D,
                        // texBold.getGlTextureId());
                        currentData = this.boldChars;
                    }
                } else if (colorIndex == 18) {
                    strikethrough = true;
                } else if (colorIndex == 19) {
                    underline = true;
                } else if (colorIndex == 20) {
                    italic = true;

                    if (bold) {
                        GlStateManager.bindTexture(texItalicBold.getGlTextureId());
                        // GL11.glfunc_179144_i(GL11.GL_TEXTURE_2D,
                        // texItalicBold.getGlTextureId());
                        currentData = this.boldItalicChars;
                    } else {
                        GlStateManager.bindTexture(texItalic.getGlTextureId());
                        // GL11.glfunc_179144_i(GL11.GL_TEXTURE_2D,
                        // texItalic.getGlTextureId());
                        currentData = this.italicChars;
                    }
                } else if (colorIndex != 16) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
                    GlStateManager.bindTexture(tex.getGlTextureId());
                    // GL11.glfunc_179144_i(GL11.GL_TEXTURE_2D,
                    // tex.getGlTextureId());
                    currentData = this.charData;
                }
                i++;
            } else if (character < currentData.length) {
                GL11.glBegin(GL11.GL_TRIANGLES);
                drawChar(currentData, character, new Vector2f(vec2.x, vec2.y));
                GL11.glEnd();

                if (strikethrough) {
                    drawLine(new Vector2f(vec2.x, vec2.y + currentData[character].height / 2), new Vector2f(vec2.x + currentData[character].width - 8.0f, vec2.y + currentData[character].height / 2));
                }

                if (underline) {
                    drawLine(new Vector2f(vec2.x, vec2.y + currentData[character].height - 2.0f), new Vector2f(vec2.x + currentData[character].width - 8.0f, vec2.y + currentData[character].height - 2.0f));
                }

                vec2.x += currentData[character].width - 8 + this.charOffset;
            }
        }

        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glPopMatrix();

        return vec2.x / 2.0F;
    }

    @Override
    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }

        int width = 0;
        CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        int size = text.length();

        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);

            if (String.valueOf(character).equals("§")) {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);

                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                } else if (colorIndex == 17) {
                    bold = true;

                    if (italic) {
                        currentData = this.boldItalicChars;
                    } else {
                        currentData = this.boldChars;
                    }
                } else if (colorIndex == 20) {
                    italic = true;

                    if (bold) {
                        currentData = this.boldItalicChars;
                    } else {
                        currentData = this.italicChars;
                    }
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    currentData = this.charData;
                }

                i++;
            } else if (character < currentData.length) {
                width += currentData[character].width - 8 + this.charOffset;
            }
        }

        return width / 2;
    }

    public int getStringWidthCust(String text) {
        if (text == null) {
            return 0;
        }

        int width = 0;
        CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        int size = text.length();

        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);

            if (String.valueOf(character).equals("§")) {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);

                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                } else if (colorIndex == 17) {
                    bold = true;

                    if (italic) {
                        currentData = this.boldItalicChars;
                    } else {
                        currentData = this.boldChars;
                    }
                } else if (colorIndex == 20) {
                    italic = true;

                    if (bold) {
                        currentData = this.boldItalicChars;
                    } else {
                        currentData = this.italicChars;
                    }
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    currentData = this.charData;
                }

                i++;
            } else if (character < currentData.length) {
                width += currentData[character].width - 8 + this.charOffset;
            }
        }

        return (width - this.charOffset) / 2;
    }

    public void setFont(Font font) {
        super.setFont(font);
        setupBoldItalicIDs();
    }

    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        setupBoldItalicIDs();
    }

    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        texBold = setupTexture(this.font.deriveFont(Font.BOLD), this.antiAlias, this.fractionalMetrics, this.boldChars);
        texItalic = setupTexture(this.font.deriveFont(Font.ITALIC), this.antiAlias, this.fractionalMetrics, this.italicChars);
        texItalicBold = setupTexture(this.font.deriveFont(Font.BOLD | Font.ITALIC), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }

    private void drawLine(Vector2f vec2, Vector2f vec1) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth((float) 1.0);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(vec2.x, vec2.y);
        GL11.glVertex2d(vec1.x, vec1.y);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public List<String> wrapWords(String text, double width) {
        List<String> finalWords = new ArrayList<>();

        if (getStringWidth(text) > width) {
            String[] words = text.split(" ");
            String currentWord = "";
            char lastColorCode = 65535;

            for (String word : words) {
                for (int i = 0; i < word.toCharArray().length; i++) {
                    char c = word.toCharArray()[i];

                    if ((String.valueOf(c).equals("§")) && (i < word.toCharArray().length - 1)) {
                        lastColorCode = word.toCharArray()[(i + 1)];
                    }
                }

                if (getStringWidth(currentWord + word + " ") < width) {
                    currentWord = currentWord + word + " ";
                } else {
                    finalWords.add(currentWord);
                    currentWord = "" + lastColorCode + word + " ";
                }
            }

            if (currentWord.length() > 0) if (getStringWidth(currentWord) < width) {
                finalWords.add("" + lastColorCode + currentWord + " ");
            } else {
                finalWords.addAll(formatString(currentWord, width));
            }
        } else {
            finalWords.add(text);
        }

        return finalWords;
    }

    public List<String> formatString(String string, double width) {
        List<String> finalWords = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        char lastColorCode = 65535;
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if ((String.valueOf(c).equals("§")) && (i < chars.length - 1))
            {
                lastColorCode = chars[(i + 1)];
            }

            if (getStringWidth(currentWord.toString() + c) < width) {
                currentWord.append(c);
            } else {
                finalWords.add(currentWord.toString());
                currentWord = new StringBuilder("" + lastColorCode + c);
            }
        }

        if (currentWord.length() > 0) {
            finalWords.add(currentWord.toString());
        }

        return finalWords;
    }

    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; index++) {
            int noClue = (index >> 3 & 0x1) * 85;
            int red = (index >> 2 & 0x1) * 170 + noClue;
            int green = (index >> 1 & 0x1) * 170 + noClue;
            int blue = (index & 0x1) * 170 + noClue;

            if (index == 6) {
                red += 85;
            }

            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
        }
    }
}