with open("../female-names.txt", "r") as fp:
    number={}
    f = open("initials4redis.txt", "a")
    line = fp.readline().strip()
    if(line != ""):
        number[line[0]] = 0
    while line:
        if(line != ""):
            if(line[0] in number.keys()):
                number[line[0]]+=1
            else:
                number[line[0]]=1
        line = fp.readline().strip()

    for x in number.keys():
        f.write("{} {} {}\n".format("SET", x.upper(), number[x]))
    f.close()
    fp.close()
