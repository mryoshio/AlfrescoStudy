import re, random, threading, time, urllib, urllib2

TICKET_URL="http://localhost:8080/alfresco/service/api/login?u=admin&pw=admin"
CREATE_URL="http://localhost:8080/alfresco/service/mryoshio/create_node"

class BenchThread(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        print "Instantiated %s" % threading.current_thread()

        # login & gets ticket
        s = urllib2.urlopen(TICKET_URL)
        for line in s.readlines():
            match = re.search(r"(TICKET_[^\<]+)", line)
            if match:
                self.post_params = urllib.urlencode({ "alf_ticket" : match.group(1) })

    def run(self):
        while 1:
            # create content
            print "create content %s" % threading.current_thread()
            urllib2.urlopen(CREATE_URL, self.post_params)
            time.sleep(random.randint(1, 10))
