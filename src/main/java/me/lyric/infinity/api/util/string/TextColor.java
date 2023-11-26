package me.lyric.infinity.api.util.string;

/**
 * @author 3arth
 * fixes color code rendering.
 */

public enum TextColor
{
    None()
            {
                @Override
                public String getColor()
                {
                    return "";
                }
            },
    Black(AnsiColors.BLACK)
            {
                @Override
                public String getColor()
                {
                    return BLACK;
                }
            },
    White(AnsiColors.RESET)
            {
                @Override
                public String getColor()
                {
                    return WHITE;
                }
            },
    DarkBlue(AnsiColors.BLUE)
            {
                @Override
                public String getColor()
                {
                    return DARK_BLUE;
                }
            },
    DarkGreen(AnsiColors.GREEN)
            {
                @Override
                public String getColor()
                {
                    return DARK_GREEN;
                }
            },
    DarkAqua(AnsiColors.CYAN)
            {
                @Override
                public String getColor()
                {
                    return DARK_AQUA;
                }
            },
    DarkRed(AnsiColors.RED)
            {
                @Override
                public String getColor()
                {
                    return DARK_RED;
                }
            },
    DarkPurple(AnsiColors.MAGENTA)
            {
                @Override
                public String getColor()
                {
                    return DARK_PURPLE;
                }
            },
    Gold(AnsiColors.YELLOW)
            {
                @Override
                public String getColor()
                {
                    return GOLD;
                }
            },
    Gray(AnsiColors.WHITE)
            {
                @Override
                public String getColor()
                {
                    return GRAY;
                }
            },
    DarkGray(AnsiColors.WHITE)
            {
                @Override
                public String getColor()
                {
                    return DARK_GRAY;
                }
            },
    Blue(AnsiColors.BLUE)
            {
                @Override
                public String getColor()
                {
                    return BLUE;
                }
            },
    Green(AnsiColors.GREEN)
            {
                @Override
                public String getColor()
                {
                    return GREEN;
                }
            },
    Aqua(AnsiColors.CYAN)
            {
                @Override
                public String getColor()
                {
                    return AQUA;
                }
            },
    Red(AnsiColors.RED)
            {
                @Override
                public String getColor()
                {
                    return RED;
                }
            },
    LightPurple(AnsiColors.MAGENTA)
            {
                @Override
                public String getColor()
                {
                    return LIGHT_PURPLE;
                }
            },
    Yellow(AnsiColors.YELLOW)
            {
                @Override
                public String getColor()
                {
                    return YELLOW;
                }
            },
    Obfuscated(AnsiColors.RESET)
            {
                @Override
                public String getColor()
                {
                    return OBFUSCATED;
                }
            },
    Bold
            {
                @Override
                public String getColor()
                {
                    return BOLD;
                }
            },
    Strike
            {
                @Override
                public String getColor()
                {
                    return STRIKE;
                }
            },
    Underline
            {
                @Override
                public String getColor()
                {
                    return UNDERLINE;
                }
            },
    Italic
            {
                @Override
                public String getColor()
                {
                    return ITALIC;
                }
            },
    Reset
            {
                @Override
                public String getColor()
                {
                    return RESET;
                }
            },
    Rainbow
            {
                @Override
                public String getColor()
                {
                    return RAINBOW;
                }
            },
    RainbowHorizontal
            {
                @Override
                public String getColor()
                {
                    return RAINBOW_PLUS;
                }
            },
    RainbowVertical
            {
                @Override
                public String getColor()
                {
                    return RAINBOW_MINUS;
                }
            },
    PlayerFace
            {
                @Override
                public String getColor()
                {
                    return PLAYER_FACE;
                }
            };

    /** The '§' char every color code starts with. */
    public static final char SECTIONSIGN  = '\u00A7';
    /** $ + 0 */
    public static final String BLACK        = SECTIONSIGN + "0";
    /** $ + 1 */
    public static final String DARK_BLUE    = SECTIONSIGN + "1";
    /** $ + 2 */
    public static final String DARK_GREEN   = SECTIONSIGN + "2";
    /** $ + 3 */
    public static final String DARK_AQUA    = SECTIONSIGN + "3";
    /** $ + 4 */
    public static final String DARK_RED     = SECTIONSIGN + "4";
    /** $ + 5 */
    public static final String DARK_PURPLE  = SECTIONSIGN + "5";
    /** $ + 6 */
    public static final String GOLD         = SECTIONSIGN + "6";
    /** $ + 7 */
    public static final String GRAY         = SECTIONSIGN + "7";
    /** $ + 8 */
    public static final String DARK_GRAY    = SECTIONSIGN + "8";
    /** $ + 9 */
    public static final String BLUE         = SECTIONSIGN + "9";
    /** $ + a */
    public static final String GREEN        = SECTIONSIGN + "a";
    /** $ + b */
    public static final String AQUA         = SECTIONSIGN + "b";
    /** $ + c */
    public static final String RED          = SECTIONSIGN + "c";
    /** $ + d */
    public static final String LIGHT_PURPLE = SECTIONSIGN + "d";
    /** $ + e */
    public static final String YELLOW       = SECTIONSIGN + "e";
    /** $ + f */
    public static final String WHITE        = SECTIONSIGN + "f";
    /** $ + k */
    public static final String OBFUSCATED   = SECTIONSIGN + "k";
    /** $ + l */
    public static final String BOLD         = SECTIONSIGN + "l";
    /** $ + m */
    public static final String STRIKE       = SECTIONSIGN + "m";
    /** $ + n */
    public static final String UNDERLINE    = SECTIONSIGN + "n";
    /** $ + o */
    public static final String ITALIC       = SECTIONSIGN + "o";
    /** $ + r */
    public static final String RESET        = SECTIONSIGN + "r";
    // TODO: if the alpha has a 0 at the hex start Integer.toHexString might not work?
    /** $ + "z" + 32-bit Hex String HAS to follow. */
    public static final String CUSTOM       = SECTIONSIGN + "z";
    /** $ + "y" */
    public static final String RAINBOW      = SECTIONSIGN + "y";
    /** $ + "+" */
    public static final String RAINBOW_PLUS = SECTIONSIGN + "+";
    /** $ + "+" */
    public static final String RAINBOW_MINUS = SECTIONSIGN + "-";
    /** $ + "p" + UUID of player must follow. */
    public static final String PLAYER_FACE = SECTIONSIGN + "p";

    private final String ansi;

    TextColor() {
        this("");
    }

    TextColor(String ansi) {
        this.ansi = ansi;
    }

    public String getAnsiCode() {
        return ansi;
    }

    /**
     * @return the colorCode belonging to the color.
     */
    public abstract String getColor();

    public static class AnsiColors {
        public static final String BLACK = "\033[0;30m";
        public static final String RED = "\033[0;31m";
        public static final String GREEN = "\033[0;32m";
        public static final String YELLOW = "\033[0;33m";
        public static final String BLUE = "\033[0;34m";
        public static final String MAGENTA = "\033[0;35m";
        // is somehow orange in Intellij?
        public static final String CYAN = "\033[0;36m";
        public static final String WHITE = "\033[0;37m";
        public static final String RESET = "\033[0m";

    }

}