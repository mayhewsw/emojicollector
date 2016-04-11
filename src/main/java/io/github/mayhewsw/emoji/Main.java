package io.github.mayhewsw.emoji;

import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.lbjava.classify.TestDiscrete;
import edu.illinois.cs.cogcomp.lbjava.learn.BatchTrainer;
import emoji4j.EmojiUtils;
import io.github.mayhewsw.emoji.lbjava.EmojiClassifier;
import io.github.mayhewsw.emoji.lbjava.EmojiLabel;

import java.io.FileNotFoundException;

/**
 * Created by mayhew2 on 4/5/16.
 */
public class Main {

    private static final String trainFolder = "Train";
    private static final String testFolder = "Test";

    private static final int outputEvery = 10000;

    public void train() throws FileNotFoundException {
        BatchTrainer trainer = new BatchTrainer(new EmojiClassifier(),
                new TweetReader("tweets.train"), outputEvery);
        trainer.train(50);
    }

    public void test() throws FileNotFoundException {
        TestDiscrete tester = new TestDiscrete();
        //tester.addNull(NERDataReader.CANDIDATE);
        TestDiscrete.testDiscrete(tester, new EmojiClassifier(), new EmojiLabel(),
                new TweetReader("tweets.test"), true, outputEvery);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Main trainer = new Main();
        trainer.train();
        trainer.test();

        //TweetReader t = new TweetReader("tweets.txt.bak");
//        Tweet tt = (Tweet) t.next();
//        System.out.println(tt.getTweetId());
//        System.out.println(tt.getTokens());
//        System.out.println(tt.emoji);

    }
}
