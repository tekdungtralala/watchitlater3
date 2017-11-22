import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import * as _ from 'lodash';

import {ServerService} from '../app-util/server.service';
import {AppScope} from '../app.scope.service';
import {MovieFavoriteModel, MovieModel, RestException, UserModel} from '../app-util/server.model';
import {NgbModal, NgbModalOptions, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {MovieDetailComponent} from '../app-shared-component/movie-detail.component/movie-detail.component';
import {DashboardScope} from './dashboard.scope.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  movies: MovieModel[];
  loggedUser: UserModel;
  @ViewChild('initialIt') private initialRef: ElementRef;
  userId: string;
  editInitial: boolean;
  errorMsg: string;
  errorMsgPP: string;
  isHideUploadBtn: boolean = true;
  private validFormat: string[] = ['image/gif', 'image/jpeg', 'image/png'];
  private fileType: string;

  constructor(private serverService: ServerService,
              private router: Router,
              private modalService: NgbModal,
              private rootScope: AppScope,
              private dashboardScope: DashboardScope) {
  }

  private initMovies(): void {
    this.movies = this.rootScope.getFavoriteMovie();
    this.movies.forEach((movie => {
      movie.imageUrl = this.serverService.getMoviePosterUrl(movie.imdbId);
    }));
    this.movies = _.orderBy(this.movies, ['imdbRating'], ['desc']);
  }

  ngOnInit(): void {
    this.initMovies();
    this.loggedUser = _.cloneDeep(this.rootScope.getUser());

    this.dashboardScope.getScope().subscribe(() => {
      this.initMovies();
    });

    this.userId = this.serverService.getDomain() + '/api/user/profile-picture/' + this.rootScope.getUser().userId;
  }

  ngOnDestroy(): void {
    this.dashboardScope.removeScope();
  }

  toggleUploadBtn($event) {
    this.isHideUploadBtn = $event.type !== 'mouseover';
  }

  onFileChange(event): void {
    this.errorMsgPP = null;
    const reader = new FileReader();
    if (event.target.files && event.target.files.length > 0) {
      const file = event.target.files[0];
      const isValid = _.find(this.validFormat, (f: string) => f === file.type);
      const isToLarge = file.size > 1500000;

      if (!isValid) {
        this.errorMsgPP = 'Image type only!';
      }
      if (isToLarge) {
        this.errorMsgPP = 'Max size only 2MB!';
      }
      if (!this.errorMsgPP) {
        this.fileType = file.type;
        reader.onload = this.handleReaderLoaded.bind(this);
        reader.readAsBinaryString(file);
      }
    }
  }

  handleReaderLoaded(readerEvt): void {
    const binaryString = readerEvt.target.result;
    const stringBase64 = btoa(binaryString);
    this.serverService.editUser({
      fileType: this.fileType,
      base64ProfilePicture: stringBase64
    }).subscribe(() => {
      this.userId = this.serverService.getDomain() + '/api/user/profile-picture/default';
      this.serverService.me().subscribe((loggedUser: UserModel) => {
        this.userId = this.serverService.getDomain() + '/api/user/profile-picture/' + loggedUser.userId;
      });
    });
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
      size: 'lg',
      windowClass: 'movie-detail-window'
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
    if (this.loggedUser.initial == null || this.loggedUser.initial.trim() === '') {
      this.errorMsg = 'Please fill initial field.';
      return;
    }
    this.errorMsg = null;
    this.serverService.editUser({initial: this.loggedUser.initial}).subscribe(() => {
      this.editInitial = false;
      this.rootScope.setUser(this.loggedUser);
    }, (error: RestException) => {
      this.errorMsg = error.error.message;
    });
  }

}
