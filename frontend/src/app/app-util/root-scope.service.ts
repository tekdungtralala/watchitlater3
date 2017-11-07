import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {isUndefined} from 'util';

import {RootScopeKey, RootScopeModel} from './fe.model';
import {MovieModel, UserModel} from './server.model';

@Injectable()
export class RootScopeService {

  private favoriteMovie: MovieModel[];
  private loggedUser: UserModel;
  private subject: Subject<RootScopeModel>;

  constructor() {
    this.subject = new Subject();
  }

  setUser(val: UserModel) {
    this.loggedUser = val;
    this.subject.next({key: RootScopeKey.HAS_USER, value: val});
  }

  isHasUser(): boolean {
    return this.loggedUser !== null;
  }

  getUser(): UserModel {
    return this.loggedUser;
  }

  emptyFavoriteMovie(): boolean {
    return isUndefined(this.favoriteMovie);
  }

  getFavoriteMovie(): MovieModel[] {
    return this.favoriteMovie;
  }

  setFavoriteMovie(val: MovieModel[]) {
    this.favoriteMovie = val;
  }

  addToFavoriteMovie(val: MovieModel) {
    this.favoriteMovie.push(val);
  }

  getSubject(): Subject<RootScopeModel> {
    return this.subject;
  }
}
