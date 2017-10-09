export class LoadingSpinnerModel {
  key: string;
  isShown: boolean;

  constructor(k: string, s: boolean) {
    this.key = k;
    this.isShown = s;
  }
}
