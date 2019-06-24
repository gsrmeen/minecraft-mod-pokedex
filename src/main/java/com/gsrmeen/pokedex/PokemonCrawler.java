package com.gsrmeen.pokedex;

import net.minecraft.util.text.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokemonCrawler {
    public static final String TYPE_TABLE_STYLE = "border: 2px solid #111111; text-align:center; background:#555555; margin-right: 5px; margin-bottom: 5px;";
    private String pokemon;
    private static String URL_TEMPLATE = "https://pixelmonmod.com/wiki/index.php?title=";

    public PokemonCrawler(String pokemon) {
        this.pokemon = pokemon;
    }

    public String getDescription() {
        try {
            Document document = Jsoup.connect(this.getUrl()).get();
            Element content = document.selectFirst("div.mw-parser-output").selectFirst("p");
            return content.text();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }

    public boolean pokemonValid() {
        return true;
    }

    public ITextComponent getTypeInfo() {
        try {

            Document document = Jsoup.connect(getUrl()).get();
            Element typeArray = document.selectFirst("table[style='" + TYPE_TABLE_STYLE + "']");
            Elements tds = typeArray.select("td");

            List<PokemonType> types = Arrays.asList(PokemonType.values());
            List<Double> multipliers = new ArrayList<>();

            for (Element td : tds) {
                multipliers.add(multiplierToDouble(td.text()));
            }


            ITextComponent msg = new TextComponentString("Weak against: ");
            for (int i = 0; i < types.size(); i++) {
                if (multipliers.get(i) > 1) {
                    String content = String.format("%s (%.0f) ", types.get(i), multipliers.get(i));
                    TextComponentString c = getFormattedTextComponent(content, types.get(i).getChatColor());
                    msg.appendSibling(c);
                }
            }

            msg.appendSibling(new TextComponentString("\nTough against: "));

            for (int i = 0; i < types.size(); i++) {
                if (multipliers.get(i) < 1) {
                    String content = String.format("%s (%.2f) ", types.get(i), multipliers.get(i));
                    TextComponentString c = getFormattedTextComponent(content, types.get(i).getChatColor());
                    msg.appendSibling(c);
                }
            }

            return msg;
        } catch (IOException e) {
            e.printStackTrace();
            ITextComponent error = new TextComponentString(e.getLocalizedMessage());
            return error;
        }
    }

    private TextComponentString getFormattedTextComponent(String msg, TextFormatting color) {
        TextComponentString rc = new TextComponentString(msg);
        rc.setStyle(new Style().setColor(color));
        return rc;
    }

    private double multiplierToDouble(String text) {
        if (text.contains("4")) {
            return 4;
        } else if (text.contains("2")) {
            return 2;
        } else if (text.contains("1")) {
            return 1;
        } else if (text.contains("0")) {
            return 0;
        } else if (text.contains("½")) {
            return 0.5;
        } else if (text.contains("¼")) {
            return 0.25;
        } else {
            return 0;
        }
    }

    private String getUrl() {
        return URL_TEMPLATE + pokemon;
    }
}
