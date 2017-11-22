import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {isUndefined} from 'util';
import * as _ from 'lodash';

import {RootScopeKey, RootScopeModel} from './app-util/fe.model';
import {MovieModel, UserModel} from './app-util/server.model';

@Injectable()
export class AppScope {

  private favoriteMovie: MovieModel[];
  private loggedUser: UserModel;
  private scope: Subject<RootScopeModel>;

  constructor() {
    this.scope = new Subject();
  }

  setUser(val: UserModel) {
    this.loggedUser = val;
    this.scope.next({key: RootScopeKey.HAS_USER, value: val});
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

  removeFromFavoriteMovie(movie: MovieModel) {
    _.remove(this.favoriteMovie, (m: MovieModel) => m.id === movie.id);
  }

  getScope(): Subject<RootScopeModel> {
    return this.scope;
  }
}
