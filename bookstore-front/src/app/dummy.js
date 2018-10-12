"use strict";
exports.__esModule = true;
var Observable_1 = require("rxjs/Observable");
function greeter() {
    Observable_1.Observable.from([1, 2, 3, 4, 5])
        .map(function (x) { return x * 2; })
        .filter(function (x) { return x > 5; })
        .subscribe(function (x) { return console.log(x); }); // 6, 8, 10
}
greeter();
