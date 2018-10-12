import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import {BookService} from '../service';
import { Book } from '../service/model/Book';

@Component({
  selector: 'bs-book-detail',
  templateUrl: './book-detail.component.html',
  styles: []
})
export class BookDetailComponent implements OnInit {

  private book: Book = new class implements Book {
    description: string;
    id: number;
    imageURL: string;
    isbn: string;
    language: Book.LanguageEnum;
    nbOfPages: number;
    publicationDate: Date;
    title: string;
    unitCost: number;
  }();

  constructor(private router: Router, private route: ActivatedRoute, private bookService: BookService) { }

  ngOnInit() {
    this.route.params
      .map(params => params['bookId'])
      .switchMap(id => this.bookService.getBook(id))
      .subscribe(book => this.book = book);
  }

  delete() {
    this.bookService.deleteBook(this.book.id)
      .finally(() => this.router.navigate(['/book-list']))
      .subscribe();
  }
}
