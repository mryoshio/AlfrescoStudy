function create() {
    var bench = userhome.childByNamePath("bench");
    if (bench == null) {
        bench = userhome.createFolder("bench");
    }
    var fileName = Math.floor(Math.random()*10000) + ".txt";
    var f = bench.createFile(fileName);
    f.content = "sample file content: " + Math.floor(Math.random()*100000);
    f.save();
}

create();