from bench_thread import BenchThread

class Bench:
    def __init__(self, params):
        self.params = params

    def execute(self):
        for i in range(self.params["thread_num"]):
            t = BenchThread()
            t.start()
