import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash'

import {MovieFavoriteInput, MovieModel} from '../../../app-util/server.model';
import {ServerService} from '../../../app-util/server.service';
import {AppScope} from '../../../app.scope.service';
import {DashboardScope} from '../../../dashboard/dashboard.scope.service';
import {MovieReviewEventEmiter} from '../../../app-util/fe.model';

@Component({
  selector: 'app-movie-info',
  templateUrl: './movie-info.component.html',
  styleUrls: ['./movie-info.component.css']
})
export class MovieInfoComponent implements OnInit {

  @Output() toggleShowMoreOutput: EventEmitter<boolean> = new EventEmitter();
  @Output() toggleShowReviewOutput: EventEmitter<MovieReviewEventEmiter> = new EventEmitter();

  @Input() movie: MovieModel;
  @Input() movies: MovieModel[];
  isShowMore: boolean;
  isFavorite: boolean;
  hasLoggedUser: boolean;

  constructor(private serverService: ServerService,
              private rootScope: AppScope,
              private dashboardScope: DashboardScope) {
  }

  ngOnInit() {
    this.movie.jsonObj = JSON.parse(this.movie.json);

    if (this.movies != null) {
      _.forEach(this.movies, (m: MovieModel) => {
        m.jsonObj = JSON.parse(m.json);
      });
    }

    this.hasLoggedUser = this.rootScope.isHasUser();
    this.updateFavoriteFlag();
  }

  toggleShowMore(): void {
    this.isShowMore = !this.isShowMore;
    this.toggleShowMoreOutput.emit(this.isShowMore);
  }

  showReview(): void {
    this.toggleShowReviewOutput.emit({isShowReview: true, movie: this.movie});
  }

  onPrevMovie(): void {
    this.updateCurrentMovie(this.findMovieIndex() - 1);
  }

  onNextMovie(): void {
    this.updateCurrentMovie(this.findMovieIndex() + 1);
  }

  updateCurrentMovie(index: number): void {
    const total = this.movies.length
    if (index < 0) {
      index = total - 1;
    }
    if (index >= total) {
      index = 0;
    }

    this.movie = this.movies[index];
    this.updateFavoriteFlag();
  }

  toggleFavorite(): void {
    this.isFavorite = !this.isFavorite;
    const data: MovieFavoriteInput = {movieId: this.movie.id, favorite: this.isFavorite};
    this.serverService.updateMovieFavorite(data);

    if (this.isFavorite) {
      this.rootScope.addToFavoriteMovie(this.movie);
    } else {
      this.rootScope.removeFromFavoriteMovie(this.movie);
    }
    this.dashboardScope.updateFavMovies();
  }

  private updateFavoriteFlag(): void {
    this.isFavorite = _.find(this.rootScope.getFavoriteMovie(), (m: MovieModel) => {
      return this.movie.id === m.id;
    }) != null;
  }

  private findMovieIndex(): number {
    return _.findIndex(this.movies, (m: MovieModel) => {
      return this.movie.imdbId === m.imdbId;
    });
  }

}
