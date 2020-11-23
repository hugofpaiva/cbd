capicua = function () {
    var numbers = db.phones.find({},{"display": 1, "_id": 0}).toArray();

    var capicuas = []
    numbers.forEach(element => {
        if(element.display == element.display.split('').reverse().join('')){
            capicuas.push(element.display)
        }

    });

    return capicuas
}
  