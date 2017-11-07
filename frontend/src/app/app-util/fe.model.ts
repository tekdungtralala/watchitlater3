import {UserModel} from './server.model';

export enum RootScopeKey {
  HAS_USER
}

export class RootScopeModel {
  key: RootScopeKey;
  value: UserModel | string;
}
