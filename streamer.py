# -*- coding: utf-8 -*-
from twython import Twython
from twython import TwythonStreamer

from tokens import *

with open("emojis.txt") as f:
    d = {}
    for line in f:
        if line.startswith("#"):
            name = line.strip()[2:]
        else:
            emojis = line.strip().split()
            d[name] = emojis


def printtweet(data):
    print data["user"]["screen_name"]
    print data["text"].encode('utf-8')
    print data["id"]
    print

class MyStreamer(TwythonStreamer):
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
                        printtweet(data)
                        return

    def on_error(self, status_code, data):
        # Want to stop trying to get data because of the error?
        # Uncomment the next line!
        # self.disconnect()
        print "error...", status_code
        pass

stream = MyStreamer(APP_KEY, APP_SECRET,
                    OAUTH_TOKEN, OAUTH_TOKEN_SECRET)
stream.statuses.sample()
