import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import * as _ from 'lodash';

import { ServerService } from './server.service';
import { RootScopeService } from './root-scope.service';
import { Observer } from 'rxjs/Observer';
import {MovieFavoriteModel, MovieModel} from "./server.model";

@Injectable()
export class AuthGuard implements CanActivate {
  observer: Observer<boolean>;
  mustHasUser: boolean;
  mustEmptyUser: boolean;
  inAnyPath: boolean;
  hasLoggedUser: boolean;
  hasAccess: boolean;


  constructor(private serverService: ServerService, private router: Router, private rootScope: RootScopeService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
    const hasUserUrls = ['/dashboard'];
    const emptyUserUrls = ['/register', '/login'];
    const anyUserUrls = ['/', '/top100', '/latest'];
    const url = state.url;

    this.mustHasUser = _.find(hasUserUrls, (x: string) => { return url.indexOf(x) === 0; }) != null;
    this.mustEmptyUser = _.find(emptyUserUrls, (x: string) => { return url.indexOf(x) === 0; }) != null;
    this.inAnyPath = _.find(anyUserUrls, (x: string) => { return url === x; }) != null;

    return new Observable<boolean>((obs: Observer<boolean>) => {
      this.observer = obs;
      this.serverService.me().subscribe(() => {
        this.afterGetUser(true);
      }, () => {
        this.afterGetUser(false);
      });
    });
  }

  private afterGetUser(val: boolean): void {
    this.hasLoggedUser = val;
    this.hasAccess = (this.inAnyPath || (this.hasLoggedUser && this.mustHasUser) ||
      (!this.hasLoggedUser && this.mustEmptyUser)) ? true : false;

    this.rootScope.setHasUser(this.hasLoggedUser);
    if (this.hasLoggedUser && this.rootScope.emptyFavoriteMovie()) {
      this.serverService.getMovieFavorite().subscribe((result: MovieFavoriteModel[]) => {
        this.afterGetMovieFavorite(result);
      }, () => {
        this.completeAuthGuard();
      });
    } else {
      this.completeAuthGuard();
    }
  }

  private afterGetMovieFavorite(val: MovieFavoriteModel[]): void {
    const movieIds: number[] = [];
    _.forEach(val, (m: MovieFavoriteModel) => {
      movieIds.push(m.movieId);
    });

    this.serverService.getMovieByMovieIds(movieIds).subscribe((result: MovieModel[]) => {
      this.rootScope.setFavoriteMovie(result);
      this.completeAuthGuard();
    }, () => {
      this.completeAuthGuard();
    });


  }

  private completeAuthGuard(): void {
    this.observer.next(this.hasAccess);
    this.observer.complete();

    if ( !this.hasAccess ) {
      if ( this.hasLoggedUser ) {
        this.router.navigate(['/dashboard']);
      } else {
        this.router.navigate(['/login']);
      }
    }
  }

}
