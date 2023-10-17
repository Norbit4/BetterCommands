package pl.norbit.bettercommands.utils;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {

    static public final String CODES = "((?<=%1$s)|(?=%1$s))";

    public static String format(String text){

        String[] texts = text.split(String.format(CODES, "&"));

        var finalText = new StringBuilder();

        for (int i = 0; i < texts.length; i++){
            if (texts[i].equalsIgnoreCase("&")){
                i++;
                if (texts[i].charAt(0) == '#'){
                    finalText.append(ChatColor.of(texts[i].substring(0, 7)) + texts[i].substring(7));
                }else{
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                }
            }else{
                finalText.append(texts[i]);
            }
        }
        return finalText.toString();
    }
}
