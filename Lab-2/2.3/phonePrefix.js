phonePrefix = function () {return db.phones.aggregate([{$group: {_id: "$components.prefix", sumPhones: {$sum: 1}}}])}

