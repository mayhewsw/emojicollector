package io.github.mayhewsw.emoji;

import edu.illinois.cs.cogcomp.core.io.LineIO;
import emoji4j.EmojiUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by mayhew2 on 4/5/16.
 */
public class GetDataStats {

    public static void getStats(String fname) throws IOException {
        List<String> lines = LineIO.read(fname);

        TreeMap<String, Integer> map = new TreeMap<>();

        int i = 0;

        for(String line : lines){
            List<String> emojis = EmojiUtils.getEmojis(line);
            for(String emoji : emojis){
                if(!map.containsKey(emoji)){
                    map.put(emoji, 0);
                }

                map.put(emoji, map.get(emoji)+1);
            }

            i++;
            if (i > 1000) break;
        }

        Map<String, Integer> smap = sortByValue(map);

        List<String> outlines = new ArrayList<>();
        outlines.add("<table class='table'>");

        outlines.add("<tr><th>Frequency</th><th>Emoji</th></tr>");

        for(String key : smap.keySet()){
            List<String> tds = new ArrayList<>();
            int num = smap.get(key);
            tds.add(num + "");
            tds.add(key);
            //tds.add(EmojiUtils.emojify(key));
            //tds.add(EmojiUtils.hexHtmlify(key));
            //tds.add(EmojiUtils.htmlify(key));

            String line = "<tr><td>" + StringUtils.join(tds, "</span></td><td><span class='replace'>")+ "</span></td></tr>";
            outlines.add(line);
        }
        outlines.add("</table>");

        LineIO.write("site/table.html", outlines);

    }

    /**
     * Super ugly...
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        getStats("tweets.train");
    }

}
