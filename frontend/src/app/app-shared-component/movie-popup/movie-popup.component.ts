import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {MovieModel} from '../../app-util/server.model';
import {MovieReviewEventEmiter} from "../../app-util/fe.model";

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

  toggleShowReview(val: MovieReviewEventEmiter): void {
    this.isShowReview = val.isShowReview;
    this.isShowMore = false;
    this.movie = val.movie;
  }

}
