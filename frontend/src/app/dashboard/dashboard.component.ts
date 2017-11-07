import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as _ from 'lodash';

import { ServerService } from '../app-util/server.service';
import { RootScopeService } from '../app-util/root-scope.service';
import {MovieFavoriteModel, MovieModel} from '../app-util/server.model';
import {NgbModal, NgbModalOptions, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {MovieDetailComponent} from "../app-shared-component/movie-detail.component/movie-detail.component";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  movies: MovieModel[];

  constructor(private serverService: ServerService,
              private router: Router,
              private modalService: NgbModal,
              private rootScope: RootScopeService) {
  }

  ngOnInit() {
    // this.serverService.getMovieFavorite().subscribe((response: MovieFavoriteModel[]) => {
    //   const movieIds: number[] = [];
    //   _.forEach(response, (m: MovieFavoriteModel) => {
    //     movieIds.push(m.movieId);
    //   });
    //
    //   this.serverService.getMovieByMovieIds(movieIds).subscribe((result: MovieModel[]) => {
    //     this.rootScope.setFavoriteMovie(result);
    //   });
    // });

    this.movies = this.rootScope.getFavoriteMovie();
    this.movies.forEach((movie => {
      movie.imageUrl = this.serverService.getMoviePosterUrl(movie.imdbId);
    }));
  }

  onSignOut() {
    this.serverService.logout().subscribe(() => {
      this.router.navigate(['/']);
    }, () => {
      this.router.navigate(['/']);
    });
  }

  open(movie: MovieModel) {
    const options: NgbModalOptions = {
      backdrop: 'static',
      size: 'lg'
    };
    const modalRef: NgbModalRef = this.modalService.open(MovieDetailComponent, options);
    modalRef.componentInstance.movie = movie;
    modalRef.componentInstance.movies = this.movies;
  }

}
