#!/usr/bin/env python
# -*- coding: utf-8 -*- 

# this will be a script to scrape emojipedia

from bs4 import BeautifulSoup
import requests

class EmojiException(Exception):
	pass

baseurl = u"http://emojipedia.org/"


def getDesc(emoji):
	page = requests.get(baseurl + emoji)
	soup = BeautifulSoup(page.text)
	title = soup.title.string	
	section = soup.find_all("section", "description")
	if len(section) == 0:
		raise EmojiException
	return title, section[0].p.text

out = open("emojidesc.txt", "w")
with open("emojis.txt") as e:
	for line in e:
		if "#" in line:
			out.write(line)
			continue
		emojis = line.strip().split()
		for emoji in emojis:
			if len(emoji) == 0: 
				continue
			print emoji,type(emoji)
			emoji = emoji.strip().decode("utf-8")
			try:
				title, d = getDesc(emoji)
				print title, d
				out.write(emoji.encode("utf-8") + "\t" + title.encode("utf-8") + "\t" + d.encode("utf-8") + "\n")
			except EmojiException:
				print emoji, "apparently has no page!"
			
out.close()
			