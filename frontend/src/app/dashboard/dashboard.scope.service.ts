import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {Observer} from 'rxjs/Observer';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class DashboardScope {
  private scope: Observable<any>;
  private observer: Observer<any>;

  constructor() {
  }

  public getScope() {
    if (!this.scope) {
      this.scope = Observable.create((observer: Observer<any>) => {
        this.observer = observer;
      });
    }
    return this.scope;
  }

  public removeScope() {
    this.observer.complete();
  }

  public updateFavMovies() {
    if (this.scope) {
      this.observer.next(true);
    }
  }


}
