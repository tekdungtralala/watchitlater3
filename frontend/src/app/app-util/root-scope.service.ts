import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { isUndefined } from 'util';

import { RootScopeKey, RootScopeModel } from './fe.model';
import { MovieModel } from './server.model';

@Injectable()
export class RootScopeService {

  private favoriteMovie: MovieModel[];
  private hasUser: boolean;
  private subject: Subject<RootScopeModel>;

  constructor() {
    this.subject = new Subject();
  }

  setHasUser(val: boolean) {
    this.hasUser = val;
    this.subject.next({ key: RootScopeKey.HAS_USER, value: val });
  }

  isHasUser(): boolean {
    return this.hasUser;
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

  getSubject(): Subject<RootScopeModel> {
    return this.subject;
  }
}
