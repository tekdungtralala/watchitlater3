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

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private serverService: ServerService, private router: Router, private rootScope: RootScopeService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
    const hasUserUrls = ['/dashboard'];
    const emptyUserUrls = ['/register', '/login'];
    const anyUserUrls = ['/', '/top100', '/latest'];
    const url = state.url;

    const mustHasUser = _.find(hasUserUrls, (x: string) => { return url.indexOf(x) === 0; }) != null;
    const mustEmptyUser = _.find(emptyUserUrls, (x: string) => { return url.indexOf(x) === 0; }) != null;
    const inAnyPath = _.find(anyUserUrls, (x: string) => { return url === x; }) != null;

    return new Observable<boolean>((observer: Observer<boolean>) => {
      this.serverService.me().subscribe(() => {
          this.afterGetUser(observer, true, mustHasUser, mustEmptyUser, inAnyPath);
        }, () => {
          this.afterGetUser(observer, false, mustHasUser, mustEmptyUser, inAnyPath);
        }
      );
    });
  }

  private afterGetUser(observer: Observer<boolean>, loggedUser: boolean, mustHasUser: boolean, mustEmptyUser: boolean,
                       inAnyPath: boolean): void {
    this.rootScope.setHasUser(loggedUser);
    const isHasAccess = (inAnyPath || (loggedUser && mustHasUser) || (!loggedUser && mustEmptyUser)) ? true : false;
    observer.next(isHasAccess);
    observer.complete();
    if ( !isHasAccess ) {
      if ( loggedUser ) {
        this.router.navigate(['/dashboard']);
      } else {
        this.router.navigate(['/login']);
      }
    }
  }
}
