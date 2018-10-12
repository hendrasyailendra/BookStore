import { Observable } from 'rxjs/Observable';


function greeter() {
    Observable.from([1, 2, 3])
        .map(x => 10 * x)
        .filter(x => x > 25)
        .subscribe(x => console.log(x));
}

greeter();