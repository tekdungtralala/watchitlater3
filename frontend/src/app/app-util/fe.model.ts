export enum RootScopeKey {
  HAS_USER
}

export class RootScopeModel {
  key: RootScopeKey;
  value: boolean | string;
}
