# -*- coding: utf-8 -*-
from twython import Twython

from tokens import *

twitter = Twython(APP_KEY, APP_SECRET, oauth_version=2)
ACCESS_TOKEN = twitter.obtain_access_token()

twitter = Twython(APP_KEY, access_token=ACCESS_TOKEN)

tweets = twitter.search(q="python", lang="en")


with open("gathered.txt", "w") as out:
    for tweet in tweets["statuses"]:
        #out.write(str(tweet))
        out.write(tweet["user"]["screen_name"] + " ")
        out.write(tweet["text"].encode("utf8") + "\n")
        out.write("\n")
        #print tweet["quoted_status_id"]



auth = twitter.get_authentication_tokens(callback_url='http://mysite.com/callback')
OAUTH_TOKEN = auth['oauth_token']
OAUTH_TOKEN_SECRET = auth['oauth_token_secret']

twitter = Twython(APP_KEY, APP_SECRET,
                  OAUTH_TOKEN, OAUTH_TOKEN_SECRET)
