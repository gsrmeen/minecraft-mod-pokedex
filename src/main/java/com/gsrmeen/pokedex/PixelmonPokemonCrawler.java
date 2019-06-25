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

public class PixelmonPokemonCrawler implements IPokemonCrawler {
    private String pokemon;
    private Document document;

    private static String URL_TEMPLATE = "https://pixelmonmod.com/wiki/index.php?title=";
    private static final String TYPE_TABLE_STYLE = "border: 2px solid #111111; text-align:center; background:#555555; margin-right: 5px; margin-bottom: 5px;";
    private static final String TOTAL_TABLE_STYLE = "border-radius: 5px; -moz-border-radius: 5px; -webkit-border-radius: 5px; -khtml-border-radius: 5px; -icab-border-radius: 5px; -o-border-radius: 5px;;";
    private static final String ERROR_DIV = "noarticletext mw-content-ltr";

    public PixelmonPokemonCrawler(String pokemon) {
        this.pokemon = pokemon;
        try {
            this.document = Jsoup.connect(getUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TextComponentString getDescription() {
        Element content = document.selectFirst("div.mw-parser-output").selectFirst("p");
        return new TextComponentString(content.text());
    }

    public TextComponentString getTypeInfo() {
        Element typeArray = document.selectFirst("table[style='" + TYPE_TABLE_STYLE + "']");
        Elements tds = typeArray.select("td");

        List<PokemonType> types = Arrays.asList(PokemonType.values());
        List<Double> multipliers = new ArrayList<>();

        for (Element td : tds) {
            multipliers.add(multiplierToDouble(td.text()));
        }

        TextComponentString msg = new TextComponentString("Weak against: ");
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
    }


    public TextComponentString getStats() {
        Elements tables = document.select("table");
        Element typeArray = null;
        for (Element e : tables) {
            if (e.attr("style").contains(TOTAL_TABLE_STYLE)) {
                typeArray = e;
                break;
            }
        }
        Elements ths = typeArray.select("th");


        String header = String.format("%s stats: ", pokemon);
        TextComponentString msg = new TextComponentString(header);

        List<Integer> intsFound = new ArrayList<>();
        for (Element th : ths) {
            String text = th.text();
            if (text.matches("-?\\d+")) {
                intsFound.add(Integer.parseInt(text));
            }
        }
        msg.appendSibling(generateTypesTextComponent(intsFound));

        return msg;
    }

    public boolean pokemonValid() {
        if (document == null) return false;
        Elements faultyElems = document.select("div[style='" + ERROR_DIV + "']");
        return faultyElems != null && faultyElems.size() == 0;
    }

    private TextComponentString generateTypesTextComponent(List<Integer> intsFound) {
        TextComponentString rc = new TextComponentString("");
        for (int i = 0; i < 7; i++) {
            String stat = "";
            TextFormatting color = TextFormatting.WHITE;
            switch (i) {
                case 0: {
                    stat = "HP";
                    color = TextFormatting.RED;
                    break;
                }
                case 1: {
                    stat = "ATK";
                    color = TextFormatting.GOLD;
                    break;
                }
                case 2: {
                    stat = "DEF";
                    color = TextFormatting.YELLOW;
                    break;
                }
                case 3: {
                    stat = "SP.ATK";
                    color = TextFormatting.BLUE;
                    break;
                }
                case 4: {
                    stat = "SP.DEF";
                    color = TextFormatting.GREEN;
                    break;
                }
                case 5: {
                    stat = "SPD";
                    color = TextFormatting.AQUA;
                    break;
                }
                case 6: {
                    stat = "TOTAL";
                    color = TextFormatting.GRAY;
                    break;
                }
            }

            String statInfo = String.format("(%s:%d) ", stat, intsFound.get(i));
            rc.appendSibling(getFormattedTextComponent(statInfo, color));

            if (i == 2) {
                rc.appendSibling(new TextComponentString("\n"));
            }
        }

        return rc;
    }


    private TextComponentString getFormattedTextComponent(String msg, TextFormatting color) {
        TextComponentString rc = new TextComponentString(msg);
        rc.setStyle(new Style().setColor(color));
        return rc;
    }

    private double multiplierToDouble(String text) {
        System.out.println("TEXT GOT: " + text);
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
