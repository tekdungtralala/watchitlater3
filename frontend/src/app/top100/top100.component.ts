import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalOptions, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import * as _ from 'lodash';

import { ServerService} from '../app-util/server.service';
import { MovieModel } from '../app-util/server.model';
import { MoviePopupComponent } from '../app-shared-component/movie-popup/movie-popup.component';
import { AppScope } from '../app.scope.service';

@Component({
  selector: 'app-top100',
  templateUrl: './top100.component.html',
  styleUrls: ['./top100.component.css']
})
export class Top100Component implements OnInit {

  top100Movies: MovieModel[];

  constructor(private serverService: ServerService,
              private modalService: NgbModal,
              private rootScope: AppScope) {
  }

  ngOnInit() {
    this.serverService.getTop100Movies().subscribe(
      ( response: MovieModel[]) => {
        response.forEach((movie => {
          movie.imageUrl = this.serverService.getMoviePosterUrl(movie.imdbId);
        }));
        this.top100Movies = response;
        this.top100Movies = _.orderBy(this.top100Movies, ['imdbRating'], ['desc']);
      }
    )
  }

  open(movie: MovieModel) {
    const options: NgbModalOptions = {
      backdrop: 'static',
      size: 'lg',
      windowClass: 'movie-detail-window'
    };
    const modalRef: NgbModalRef = this.modalService.open(MoviePopupComponent, options);
    modalRef.componentInstance.movie = movie;
    modalRef.componentInstance.movies = this.top100Movies;
  }

}
