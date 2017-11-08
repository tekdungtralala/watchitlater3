import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import * as _ from 'lodash';

import {ServerService} from '../app-util/server.service';
import {RootScopeService} from '../app-util/root-scope.service';
import {MovieFavoriteModel, MovieModel, RestException, UserModel} from '../app-util/server.model';
import {NgbModal, NgbModalOptions, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {MovieDetailComponent} from '../app-shared-component/movie-detail.component/movie-detail.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  movies: MovieModel[];
  loggedUser: UserModel;
  @ViewChild('initialit') private initialRef: ElementRef;
  editInitial: boolean;
  errorMsg: string;

  constructor(private serverService: ServerService,
              private router: Router,
              private modalService: NgbModal,
              private rootScope: RootScopeService) {
  }

  ngOnInit() {
    this.movies = this.rootScope.getFavoriteMovie();
    this.movies.forEach((movie => {
      movie.imageUrl = this.serverService.getMoviePosterUrl(movie.imdbId);
    }));

    this.movies = _.orderBy(this.movies, ['imdbRating'], ['desc']);
    this.loggedUser = _.cloneDeep(this.rootScope.getUser());
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

  toggleEditInitial(): void {
    this.editInitial = !this.editInitial;

    if (this.editInitial) {
      this.initialRef.nativeElement.focus();
    } else {
      this.loggedUser = _.cloneDeep(this.rootScope.getUser());
      this.errorMsg = null;
    }
  }

  saveInitial(): void {
    this.errorMsg = null;
    this.serverService.editUser({initial: this.loggedUser.initial}).subscribe(() => {
      this.editInitial = false;
      this.rootScope.setUser(this.loggedUser);
    }, (error: RestException) => {
      this.errorMsg = error.error.message;
    });
  }

}
