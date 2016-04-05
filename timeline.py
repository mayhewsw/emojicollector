#!/usr/bin/python

# -*- coding: utf-8 -*-
from twython import Twython

import re
from tokens import *

#twitter = Twython(APP_KEY, APP_SECRET, oauth_version=2)
#ACCESS_TOKEN = twitter.obtain_access_token()

#twitter = Twython(APP_KEY, access_token=ACCESS_TOKEN)

#tweets = twitter.search(q="python", lang="en")


#auth = twitter.get_authentication_tokens(callback_url='http://mysite.com/callback')
#OAUTH_TOKEN = auth['oauth_token']
#OAUTH_TOKEN_SECRET = auth['oauth_token_secret']

twitter = Twython(APP_KEY, APP_SECRET,
                  OAUTH_TOKEN, OAUTH_TOKEN_SECRET)

print twitter.get_user_timeline(screen_name="mayhewsw")
