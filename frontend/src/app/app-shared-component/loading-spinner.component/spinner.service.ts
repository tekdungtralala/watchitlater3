import { Subject } from 'rxjs/Subject';

export class SpinnerService {
  spinnerActivated = new Subject();
}

export class SpinnerStateModel {
  key: string;
  isShown: boolean;

  constructor(k: string, s: boolean) {
    this.key = k;
    this.isShown = s;
  }
}
