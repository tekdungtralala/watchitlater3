import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import * as _ from 'lodash';

import { MovieModel } from '../../app-util/server.model';

@Component({
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {
  @Input() movie: MovieModel;
  @Input() movies: MovieModel[];
  isShowMore: boolean = false;

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit() {
    this.movie.jsonObj = JSON.parse(this.movie.json);

    if ( this.movies != null ) {
      _.forEach(this.movies, (m: MovieModel) => {
        m.jsonObj = JSON.parse(m.json);
      })
    }
  }

  toggleShowMore() {
    this.isShowMore = !this.isShowMore;
  }

  onPrevMovie() {
    this.updateCurrentMovie(this.findMovieIndex() - 1);
  }

  onNextMovie() {
    this.updateCurrentMovie(this.findMovieIndex() + 1);
  }

  updateCurrentMovie(index: number) {
    const total = this.movies.length
    if ( index < 0 ) index = total - 1;
    if ( index >= total ) index = 0;

    this.movie = this.movies[index];
  }

  findMovieIndex(): number {
    return _.findIndex(this.movies, (m: MovieModel) => {
      return this.movie.imdbId == m.imdbId;
    });
  }
}
