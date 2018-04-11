import {AfterContentInit, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';
import * as _ from 'lodash';

import {ServerService} from '../../../app-util/server.service';
import {MovieModel, MovieReviewResp} from '../../../app-util/server.model';
import {MovieReviewEventEmiter} from '../../../app-util/fe.model';
import {AppScope} from '../../../app.scope.service';


@Component({
  selector: 'app-movie-review',
  templateUrl: './movie-review.component.html',
  styleUrls: ['./movie-review.component.css']
})
export class MovieReviewComponent implements OnInit, AfterContentInit {


  @Input() movie: MovieModel;
  @Output() toggleShowReviewOutput: EventEmitter<MovieReviewEventEmiter> = new EventEmitter();
  private static MOVIEREVIEW_LIMIT: number = 3;
  private offset: number = 0;
  listData: MovieReviewResp[] = [];
  isHideLoadMore: boolean;
  myReview: MovieReviewResp;
  isEditOwnReview: boolean;
  invalidReview: boolean;

  constructor(private rootScope: AppScope,
              private serverService: ServerService,
              private router: Router,
              private activeModal: NgbActiveModal) {
    this.myReview = new MovieReviewResp();
  }

  ngOnInit(): void {
  }

  ngAfterContentInit(): void {
    setTimeout(() => {
      this.loadReview();
    });
  }

  toggleShowReview(): void {
    this.toggleShowReviewOutput.emit({isShowReview: false, movie: this.movie});
  }

  loadReview(): void {
    this.serverService.getMovieReview(this.movie.id, this.offset).subscribe((results: MovieReviewResp[]) => {
      this.offset = this.offset + MovieReviewComponent.MOVIEREVIEW_LIMIT;
      this.isHideLoadMore = results.length < MovieReviewComponent.MOVIEREVIEW_LIMIT;
      _.forEach(results, (m) => {
        m.userAvatarUrl = this.serverService.getUserProfilePictureUrl(m.userId);
        m.isHideSigninBtn = true;
        this.listData.push(m);
      });
    });

    this.serverService.getOwnMovieReview(this.movie.id).subscribe((result: MovieReviewResp) => {
      this.myReview = result;
      this.isEditOwnReview = this.myReview.id === 0;
    });
  }

  updatePoint(point: number, movie: MovieReviewResp): void {
    if (!this.rootScope.getUser()) {
      _.forEach(this.listData, (m) => m.isHideSigninBtn = true);
      movie.isHideSigninBtn = false;
    } else {
      movie.point += point;
    }
  }

  toLoginPage(): void {
    this.activeModal.close();
    this.router.navigate(['/login']);
  }

  showEditReview(): void {
    this.isEditOwnReview = true;
    this.invalidReview = false;
  }

  cancelEditReview(): void {
    this.invalidReview = false;
    if (this.myReview.id)
      this.isEditOwnReview = false;
  }

  saveUserReview(): void {
    this.invalidReview = false;
    if (this.myReview.review) {

      this.serverService.postMovieReview(this.myReview.review, this.movie.id)
        .subscribe(result => {
          this.isEditOwnReview = false;
        });
    } else {
      this.invalidReview = true;
    }
  }

}
