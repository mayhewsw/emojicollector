# -*- coding: utf-8 -*-
from twython import Twython
from twython import TwythonStreamer
import re
from tokens import *



with open("../data/emojis.txt") as f:
    d = {}
    for line in f:
        if line.startswith("#"):
            name = line.strip()[2:]
        else:
            emojis = line.strip().split()
            d[name] = emojis



class MyStreamer(TwythonStreamer):
    def formattweet(self, data):
        text = data["text"].encode('utf-8')
        text = re.sub(r"\n", " ", text)
        text = re.sub(r"\t", " ", text)
        tid = data["id"]
        return str(tid) + "\t" + text

    def set_out(self, out):
        self.out = out
        
    def on_success(self, data):

        if "retweeted_status" in data:
            data = data["retweeted_status"]

        # conditions on which we keep the tweet.
        if 'text' in data and "lang" in data and \
                        data["lang"] == "en" and \
                        len(data["entities"]["urls"]) == 0 and "media" not in data["entities"] and \
                        data["in_reply_to_screen_name"] is None and \
                        not data["is_quote_status"]:
            t = data['text'].encode('utf-8')
            
            for emojilist in d.keys():
                for k in d[emojilist]:
                    if k in t:
                        text = self.formattweet(data)
                        print text
                        out.write(text + "\n")
                        out.flush()
                        return

    def on_error(self, status_code, data):
        # Want to stop trying to get data because of the error?
        # Uncomment the next line!
        # self.disconnect()
        print "error...", status_code
        pass


with open("../data/tweets.txt", "a") as out:
    stream = MyStreamer(APP_KEY, APP_SECRET,
                    OAUTH_TOKEN, OAUTH_TOKEN_SECRET)
    stream.set_out(out)
    stream.statuses.sample()
