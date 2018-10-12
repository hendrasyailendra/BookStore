import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Book } from '../service/model/Book';
import 'rxjs/Rx';
import { BookService } from '../service/api/BookApi';

@Component({
  selector: 'bs-book-form',
  templateUrl: './book-form.component.html',
  styles: []
})
export class BookFormComponent implements OnInit {

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

  constructor(private router: Router, private bookService: BookService) { }

  ngOnInit() {
  }

  create() {
    this.bookService.createBook(this.book)
      .finally(() => this.router.navigate(['/book-list']))
      .subscribe();
  }
}
