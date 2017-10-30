import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { RootScopeKey, RootScopeModel } from './fe.model';

@Injectable()
export class RootScopeService {

  private hasUser: boolean;
  private subject: Subject<RootScopeModel>;

  constructor() {
    this.subject = new Subject();
  }

  setHasUser(val: boolean) {
    this.hasUser = val;
    this.subject.next({key: RootScopeKey.HAS_USER, value: val});
  }

  getSubject(): Subject<RootScopeModel> {
    return this.subject;
  }


}
