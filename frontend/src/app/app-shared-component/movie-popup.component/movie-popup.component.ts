import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import * as _ from 'lodash';

import {MovieFavoriteInput, MovieModel} from '../../app-util/server.model';
import {ServerService} from '../../app-util/server.service';
import {AppScope} from '../../app.scope.service';
import {DashboardScope} from '../../dashboard/dashboard.scope.service';

@Component({
  templateUrl: './movie-popup.component.html',
  styleUrls: ['./movie-popup.component.css'],
  host: {'[class.isShowMore]': 'isShowMore'}
})
export class MoviePopupComponent implements OnInit {
  @Input() movie: MovieModel;
  @Input() movies: MovieModel[];
  isFavorite: boolean;
  isShowMore: boolean;
  hasLoggedUser: boolean;

  constructor(public activeModal: NgbActiveModal,
              private serverService: ServerService,
              private rootScope: AppScope,
              private dashboardScope: DashboardScope) {
  }

  ngOnInit() {
    this.movie.jsonObj = JSON.parse(this.movie.json);

    if (this.movies != null) {
      _.forEach(this.movies, (m: MovieModel) => {
        m.jsonObj = JSON.parse(m.json);
      })
    }

    this.hasLoggedUser = this.rootScope.isHasUser();
    this.updateFavoriteFlag();
  }

  toggleShowMore(): void {
    this.isShowMore = !this.isShowMore;
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
