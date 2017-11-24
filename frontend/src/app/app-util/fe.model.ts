import {MovieModel, UserModel} from './server.model';

export enum RootScopeKey {
  HAS_USER
}

export class RootScopeModel {
  key: RootScopeKey;
  value: UserModel | string;
}

export class MovieReviewEventEmiter {
  movie: MovieModel;
  isShowReview: boolean;
}
