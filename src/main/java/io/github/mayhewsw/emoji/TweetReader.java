package io.github.mayhewsw.emoji;

import edu.illinois.cs.cogcomp.core.io.LineIO;
import edu.illinois.cs.cogcomp.lbjava.parse.Parser;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by mayhew2 on 4/5/16.
 */
public class TweetReader implements Parser {

    List<String> lines;
    int currIndex;

    public TweetReader(String path) {
        try {
            lines = LineIO.read(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        currIndex = 0;
    }

    @Override
    public Object next() {

        if(currIndex == lines.size()){
            return null;
        }else {

            Tweet t = new Tweet(lines.get(currIndex++));
            while(t.getEmoji().size() == 0){
                t = new Tweet(lines.get(currIndex++));
            }

            return t;
        }
    }

    @Override
    public void reset() {
        currIndex = 0;
    }

    @Override
    public void close() {
        // nothing here.
    }
}
