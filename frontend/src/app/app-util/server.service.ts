import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import 'rxjs/Rx';

@Injectable()
export class ServerService {
  private domain: string = 'http://localhost:8080';

  constructor(private http: Http) {}

  me() {
    const url: string = this.domain +  '/api/auth/me';
    return this.http.get(url, {withCredentials: true});
  }

  getGitStatus() {
    const url: string = this.domain +  '/api/git-status';
    return this.http.get(url, {withCredentials: true});
  }

  login() {
    const url: string = this.domain +  '/api/auth/signin';
    return this.http.get(url, {withCredentials: true});
  }

  logout() {
    const url: string = this.domain +  '/api/auth/signout';
    return this.http.get(url, {withCredentials: true});
  }
}
