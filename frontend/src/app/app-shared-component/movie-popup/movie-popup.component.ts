import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {MovieModel} from '../../app-util/server.model';

@Component({
  templateUrl: './movie-popup.component.html',
  styleUrls: ['./movie-popup.component.css'],
  host: {'[class.isShowMore]': 'isShowMore', '[class.isShowReview]': 'isShowReview'}
})
export class MoviePopupComponent implements OnInit {
  @Input() movie: MovieModel;
  @Input() movies: MovieModel[];
  isShowMore: boolean;
  isShowReview: boolean;

  constructor(public activeModal: NgbActiveModal) {
  }

  ngOnInit() {
  }

  toggleShowMore(isShowMore: boolean): void {
    this.isShowMore = isShowMore;
    this.isShowReview = false;
  }

  toggleShowReview(isShowReview: boolean): void {
    this.isShowReview = isShowReview;
    this.isShowMore = false;
  }

}
