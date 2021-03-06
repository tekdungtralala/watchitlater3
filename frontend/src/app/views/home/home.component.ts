import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as _ from 'lodash';

import { ServerService } from '../../app-util/server.service';
import { MovieModel } from '../../app-util/server.model';
import { NgbModal, NgbModalOptions, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MoviePopupComponent } from '../../app-shared-component/movie-popup/movie-popup.component';
import { AppScope } from '../../app.scope.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  listMovieModel: MovieModel[];

  constructor(
    private serverService: ServerService,
    private router: Router,
    private modalService: NgbModal,
    private rootScope: AppScope) {

  }

  ngOnInit() {
    this.serverService.getLandingPageMovies().subscribe(
      (response: MovieModel[]) => {
        response.forEach((movie => {
          movie.imageUrl = this.serverService.getMoviePosterUrl(movie.imdbId);
        }));
        this.listMovieModel = response;
      }
    );
  }

  open(movie: MovieModel) {
    const options: NgbModalOptions = {
      backdrop: 'static',
      size: 'lg',
      windowClass: 'movie-detail-window'
    };
    const modalRef: NgbModalRef = this.modalService.open(MoviePopupComponent, options);
    modalRef.componentInstance.movie = movie;
    modalRef.componentInstance.movies = this.listMovieModel;
  }

  onLoadTop100() {
    this.router.navigate(['/top100']);
  }

  onLoadLatest() {
    this.router.navigate(['/latest']);
  }

}
