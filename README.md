## Description 
This is simple book store website using Java EE as REST API (backend), Angular (frontend), H2 (relational db), Intellij IDEA (Editor), Maven (Building tool), Wildfly (Application Server), JUnit & Arquillian (Testing Framworks).

## Structure 

The BookStore application is divided into a Java EE REST back-end (`bookstore-back`) and an Angular front-end (`bookstore-front`).


### Bookstore Back 

The code of this module follows the [Maven](http://maven.apache.org/) directory structure. The `src/main/` directory contains the main source code while you will find the test class under `src/test/`. The `pom.xml` file is Maven specific and it describes the project and its dependencies.

Once [Maven](http://maven.apache.org/) and a [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) are installed, enter the following Maven commands:

* `mvn help:help`       : shows Maven help
* `mvn clean`           : cleans the `target` directory
* `mvn compile`         : compiles the code
* `mvn test`            : runs the test case (you need WildFly to be up and running)
* `mvn package`         : packages the code into a war file
* `mvn clean package`   : executes a clean and then a package

Once [Wildfly](http://wildfly.org/) is installed, deploy the war file and go to [http://localhost:8080/bookstore-back/]()


### Bookstore Front 

The code of this module follows the [Angular CLI](https://github.com/angular/angular-cli) directory structure. The `src/` directory contains the main source code. The `package.json` file is Node specific and it describes the project and its dependencies.

Once [Node JS](https://nodejs.org/en/) and a [Yarn](yarnpkg.com) are installed, enter the following commands:

* `yarn install`        : Installs all the dependencies (adding the node_modules directory)
* `ng serve`            : Executes the server. Go to [http://localhost:4200]()


## Demo 

* Generate Services from Swagger : * Get version `swagger-codegen version` * Get help `swagger-codegen help generate` * `swagger-codegen generate -i bookstore-back/src/main/webapp/swagger.json -l typescript-angular2 -o bookstore-front/src/app/service`
* Fixing issue [https://github.com/swagger-api/swagger-codegen/issues/4397](). Change the code of `createBook` in `BookApi.ts` : ``` public createBook(body: models.Book, extraHttpRequestParams?: any): Observable<{}> { return this.createBookWithHttpInfo(body, extraHttpRequestParams) .map((response: Response) => { switch (response.status) { case 204 : return undefined; case 201 : return response; default : return response.json(); } }); } ```
* Change the `Book` interface to a class (`export class Book`)
* Add the `BookAPI` provider to the `app.module.ts` (`providers: [BookApi],`)
* Add links to the `book-list.component.html` ``` <h2>List all the books - {{nbBooks}}</h2> <ul class="list-unstyled"> <li class="media my-4" *ngFor="let book of books"> <img class="d-flex mr-3 img-thumbnail" src="{{book.imageURL}}" alt="Generic placeholder image"> <div class="media-body"> <h5 class="mt-0 mb-1"><a [routerLink]="['/book-detail', book.id]">{{book.title}}</a></h5> {{book.description}} </div> </li> </ul> ``` Add variables and API to the `book-list.component.ts` ``` private nbBooks: number; private books: Book[]; constructor(private bookAPI: BookApi) { } ngOnInit() { this.bookAPI.countBooks().subscribe(nbBooks => this.nbBooks = nbBooks); this.bookAPI.getBooks().subscribe(books => this.books = books); } ```
* Change the page `book-detail.component.html` file ``` <h2>Detail of a book</h2> <form> <div class="form-group row"> <label class="col-2 col-form-label">Title</label> <div class="col-10"> <input class="form-control" type="text" value="{{book.title}}" readonly> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">Description</label> <div class="col-10"> <textarea class="form-control" rows="3" readonly>{{book.description}}</textarea> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">Unit cost</label> <div class="col-10"> <div class="input-group"> <input class="form-control" type="number" value="{{book.unitCost}}" readonly> <span class="input-group-addon">$</span> </div> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">ISBN</label> <div class="col-10"> <input class="form-control" type="text" value="{{book.isbn}}" readonly> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">Number of pages</label> <div class="col-10"> <input class="form-control" type="number" value="{{book.nbOfPages}}" readonly> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">Language</label> <div class="col-10"> <input class="form-control" type="text" value="{{book.language}}" readonly> </div> </div> <button type="submit" class="btn btn-primary">Delete</button> <a class="btn btn-secondary" href="" role="button">Back</a> </form> ``` Change the page `book-detail.component.ts` file ``` private book: Book = new Book(); constructor(private router: Router, private route: ActivatedRoute, private bookAPI: BookApi) { } ngOnInit() { this.route.params .map(params => params['bookId']) .switchMap(id => this.bookAPI.getBook(id)) .subscribe(book => this.book = book); } delete() { this.bookAPI.deleteBook(this.book.id).subscribe(); this.router.navigate(['/book-list']); } ```
* Change the page `book-form.component.html` file ``` <h2>Create a new book</h2> <form (ngSubmit)="create()"> <div class="form-group row"> <label class="col-2 col-form-label">Title</label> <div class="col-10"> <input class="form-control" type="text" [(ngModel)]="book.title" name="title"> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">Description</label> <div class="col-10"> <textarea class="form-control" rows="3" [(ngModel)]="book.description" name="description"></textarea> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">Unit cost</label> <div class="col-10"> <div class="input-group"> <input class="form-control" type="number" [(ngModel)]="book.unitCost" name="unitCost"> <span class="input-group-addon">$</span> </div> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">Number of pages</label> <div class="col-10"> <input class="form-control" type="number" [(ngModel)]="book.nbOfPages" name="nbOfPages"> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">Image URL</label> <div class="col-10"> <input class="form-control" type="url" [(ngModel)]="book.imageURL" name="imageURL"> </div> </div> <div class="form-group row"> <label class="col-2 col-form-label">Language</label> <div class="col-10"> <select class="form-control" [(ngModel)]="book.language" name="language"> <option value="">Select</option> <option value="0">ENGLISH</option> <option value="1">FRENCH</option> <option value="2">SPANISH</option> <option value="3">PORTUGUESE</option> <option value="4">ITALIAN</option> <option value="5">FINISH</option> <option value="6">GERMAN</option> <option value="7">DUTCH</option> <option value="8">RUSSIAN</option> </select> </div> </div> <button type="submit" class="btn btn-primary">Create</button> <a class="btn btn-secondary" [routerLink]="['/book-list']" role="button">Cancel</a> </form> ``` Change the page `book-form.component.ts` file ``` private book: Book = new Book(); constructor(private router: Router, private route: ActivatedRoute, private bookAPI: BookApi) { } ngOnInit() { this.route.params .map(params => params['bookId']) .switchMap(id => this.bookAPI.getBook(id)) .subscribe(book => this.book = book); } delete() { this.bookAPI.deleteBook(this.book.id).subscribe(); this.router.navigate(['/book-list']); } ```
* To avoid CORS, create the `proxy.conf.json` file ``` { "/": { "target": "http://localhost:8080", "secure": false } } ``` Change the script in `package.json` `ng serve --proxy-config proxy.conf.json`. In the `BookAPI` change the code to `protected basePath = '/bookstore-back/api';`
* To truncate description in `book-list.component` just `yarn add angular2-truncate`, add `TruncatePipe` in `app.module.ts` in the declarations section, and use it `{{book.description | truncate : 600 }}`

*To execute the application you have to build it (`mvn clean package`) and then deploy the `bookstore-back.war` into [WildFly](https://wildfly.org).*

 # Screen Shots 
  ![alt text](https://github.com/taha7ussein007/BookStore/blob/master/scrShots/1.png)
  ![alt text](https://github.com/taha7ussein007/BookStore/blob/master/scrShots/2.png)
  ![alt text](https://github.com/taha7ussein007/BookStore/blob/master/scrShots/3.png)
  ![alt text](https://github.com/taha7ussein007/BookStore/blob/master/scrShots/4.png)
