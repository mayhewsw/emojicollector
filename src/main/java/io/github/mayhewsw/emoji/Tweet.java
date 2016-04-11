package io.github.mayhewsw.emoji;

import emoji4j.EmojiUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mayhew2 on 4/5/16.
 */
public class Tweet {
    // contains emoji
    // contains words.

    // this comes straight from: https://github.com/kcthota/emoji4j/blob/master/src/main/java/emoji4j/AbstractEmoji.java
    protected static final Pattern htmlEntityPattern = Pattern.compile("&#\\w+;");

    String tweetid;
    List<String> tokens;
    List<String> emoji;

    public Tweet(String line) {
        //System.out.println(line);
        String[] sline = line.split("\\t");

        if(sline.length != 2) {
            System.err.println("bad line length for this tweet: " + line);
            return;
        }

        tweetid = sline[0];
        String tweet = sline[1];

        String cleanline = EmojiUtils.removeAllEmojis(tweet);

        sline = cleanline.split("\\s");
        tokens = Arrays.asList(sline);

        // somehow populate the emoji...
        //emoji = EmojiUtils.getEmojis(tweet);
        emoji = EmojiUtils.getEmojis(tweet);

    }

    public List<String> getTokens(){
        return tokens;
    }

    public String getTweetId(){
        return tweetid;
    }

    public List<String> getEmoji(){
        return emoji;
    }





}
