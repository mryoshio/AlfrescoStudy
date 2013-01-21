import re, threading, urllib, urllib2

TICKET_URL="http://localhost:8080/alfresco/service/api/login?u=admin&pw=admin"
CREATE_URL="http://localhost:8080/alfresco/service/mryoshio/create_node"

class BenchThread(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        print "Instantiated %s" % threading.current_thread()
        
    def run(self):
        # login & gets ticket
        s = urllib2.urlopen(TICKET_URL)

        # puts content
        for line in s.readlines():
            match = re.search(r"(TICKET_[^\<]+)", line)
            if match:
                d = urllib.urlencode({ "alf_ticket" : match.group(1) })
                urllib2.urlopen(CREATE_URL, d)

        # logout
               
