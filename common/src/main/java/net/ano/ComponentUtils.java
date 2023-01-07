package net.ano;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;

import java.util.Arrays;
import java.util.Optional;

public class ComponentUtils {

    public static String getCoded(Component component) {
        StringBuilder result = new StringBuilder();

        component.visit(new CodedStringGenerator(result), Style.EMPTY);

        return result.toString();
    }

    public static String getCoded(String jsonString) {
        MutableComponent component = Component.Serializer.fromJson(jsonString);
        if (component == null) return null;

        return getCoded(component);
    }

    public static Optional<ChatFormatting> getChatFormatting(TextColor textColor) {
        return getChatFormatting(textColor.getValue());
    }

    public static Optional<ChatFormatting> getChatFormatting(int textColor) {
        return Arrays.stream(ChatFormatting.values())
                .filter(c -> c.isColor() && textColor == c.getColor())
                .findFirst();
    }

    private static StringBuilder tryConstructDifference(Style oldStyle, Style newStyle) {
        StringBuilder add = new StringBuilder();

        int oldColorInt = Optional.ofNullable(oldStyle.getColor())
                .map(TextColor::getValue)
                .orElse(-1);
        int newColorInt = Optional.ofNullable(newStyle.getColor())
                .map(TextColor::getValue)
                .orElse(-1);

        if (oldColorInt == -1) {
            if (newColorInt != -1) {
                getChatFormatting(newColorInt).ifPresent(add::append);
            }
        } else if (oldColorInt != newColorInt) {
            return null;
        }

        if (oldStyle.isBold() && !newStyle.isBold()) return null;
        if (!oldStyle.isBold() && newStyle.isBold()) add.append(ChatFormatting.BOLD);

        if (oldStyle.isItalic() && !newStyle.isItalic()) return null;
        if (!oldStyle.isItalic() && newStyle.isItalic()) add.append(ChatFormatting.ITALIC);

        if (oldStyle.isUnderlined() && !newStyle.isUnderlined()) return null;
        if (!oldStyle.isUnderlined() && newStyle.isUnderlined()) add.append(ChatFormatting.UNDERLINE);

        if (oldStyle.isStrikethrough() && !newStyle.isStrikethrough()) return null;
        if (!oldStyle.isStrikethrough() && newStyle.isStrikethrough()) add.append(ChatFormatting.STRIKETHROUGH);

        if (oldStyle.isObfuscated() && !newStyle.isObfuscated()) return null;
        if (!oldStyle.isObfuscated() && newStyle.isObfuscated()) add.append(ChatFormatting.OBFUSCATED);

        return add;
    }


    private static class CodedStringGenerator implements FormattedText.StyledContentConsumer<Object> {
        private final StringBuilder result;
        Style oldStyle;

        public CodedStringGenerator(StringBuilder result) {
            this.result = result;
            oldStyle = Style.EMPTY;
        }

        @Override
        public Optional<Object> accept(Style style, String string) {
            handleStyleDifference(oldStyle, style, result);
            result.append(string);

            oldStyle = style;

            return Optional.empty();
        }

        /**
         * This method handles the fact that the style likely has changed between 2 components
         *
         * <p>It tries to first generate a constructive way of adding color codes to get from the old
         * style to the new style. If that does not succeed, it instead resets the format and adds the
         * color codes of the new style
         */
        private static void handleStyleDifference(Style oldStyle, Style newStyle, StringBuilder result) {
            if (oldStyle.equals(newStyle)) return;

            if (!oldStyle.isEmpty()) {
                StringBuilder different = tryConstructDifference(oldStyle, newStyle);

                if (different != null) {
                    result.append(different);
                    return;
                }
            }

            result.append(ChatFormatting.RESET);

            if (newStyle.getColor() != null) {
                getChatFormatting(newStyle.getColor()).ifPresent(result::append);
            }

            if (newStyle.isBold()) result.append(ChatFormatting.BOLD);
            if (newStyle.isItalic()) result.append(ChatFormatting.ITALIC);
            if (newStyle.isUnderlined()) result.append(ChatFormatting.UNDERLINE);
            if (newStyle.isStrikethrough()) result.append(ChatFormatting.STRIKETHROUGH);
            if (newStyle.isObfuscated()) result.append(ChatFormatting.OBFUSCATED);
        }
    }

}
