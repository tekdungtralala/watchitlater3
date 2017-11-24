import {AfterContentInit, ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';

import {ServerService} from '../../../app-util/server.service';
import {MovieModel, MovieReviewOutput} from '../../../app-util/server.model';

@Component({
  selector: 'app-movie-review',
  templateUrl: './movie-review.component.html',
  styleUrls: ['./movie-review.component.css']
})
export class MovieReviewComponent implements OnInit, AfterContentInit {


  @Input() movie: MovieModel;
  @Output() toggleShowReviewOutput: EventEmitter<boolean> = new EventEmitter();
  private static MOVIEREVIEW_LIMIT: number = 3;
  private offset: number = 0;
  listData: MovieReviewOutput[] = [];
  isHideLoadMore: boolean;

  constructor(private cd: ChangeDetectorRef, private serverService: ServerService) {
  }

  ngOnInit(): void {

  }

  ngAfterContentInit(): void {
    setTimeout(() => {
      this.loadReview();
    });
  }

  toggleShowReview(): void {
    this.toggleShowReviewOutput.emit(false);
  }

  loadReview(): void {
    this.serverService.getMovieReview(this.movie.id, this.offset).subscribe((results: MovieReviewOutput[]) => {
      this.offset = this.offset + MovieReviewComponent.MOVIEREVIEW_LIMIT;
      this.isHideLoadMore = results.length < MovieReviewComponent.MOVIEREVIEW_LIMIT;
      _.forEach(results, (m) => {
        m.userAvatarUrl = this.serverService.getUserProfilePictureUrl(m.userId);
        this.listData.push(m);
      });
    });
  }

}
