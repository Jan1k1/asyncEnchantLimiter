
package studio.jan1k.asyncenchantlimiter.utils;

public class TextUtils {

    private static final String NORMAL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?,.:";
    private static final String SMALL_CAPS = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?,.:";

    public static String toSmallCaps(String text) {
        StringBuilder builder = new StringBuilder();
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if ((c == '&' || c == '§') && i + 1 < chars.length) {
                char next = chars[i + 1];
                if ((next >= '0' && next <= '9') || (next >= 'a' && next <= 'f') || (next >= 'A' && next <= 'F')
                        || "klmnorKLMNORxX".indexOf(next) != -1) {
                    builder.append(c);
                    builder.append(next);
                    i++;
                    continue;
                }
                if (c == '&' && next == '#' && i + 7 < chars.length) {
                    builder.append("&#");
                    for (int j = 0; j < 6; j++) {
                        builder.append(chars[i + 2 + j]);
                    }
                    i += 7;
                    continue;
                }
            }

            int index = NORMAL.indexOf(c);
            if (index != -1 && index < SMALL_CAPS.length()) {
                builder.append(SMALL_CAPS.charAt(index));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
