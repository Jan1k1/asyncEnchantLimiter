
package studio.jan1k.asyncenchantlimiter.utils;

public class TextUtils {

    private static final String NORMAL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?,.:";
    private static final String SMALL_CAPS = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?,.:";

    public static String toSmallCaps(String text) {
        StringBuilder builder = new StringBuilder();
        for (char c : text.toCharArray()) {
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
