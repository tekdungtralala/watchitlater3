import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { MovieModel } from '../../app-util/server.model';

@Component({
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {
  @Input() movie: MovieModel;
  isShowMore: boolean = false;

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit() {
    this.movie.jsonObj = JSON.parse(this.movie.json);
    console.log(this.movie.jsonObj)
  }

  toggleShowMore() {
    this.isShowMore = !this.isShowMore;
  }
}
