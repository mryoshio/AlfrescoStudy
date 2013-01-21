import sys
from bench import Bench

def bench():
   b  = Bench({ "thread_num" : int(sys.argv[1]) })
   b.execute()

if len(sys.argv) < 2 or sys.argv[1].isdigit == False:
   print "Usage: main.py <thread num>"
else:
   bench()
