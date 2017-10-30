import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import 'rxjs/Rx';
import { Observable } from 'rxjs/Observable';

import { MovieGroupNameModel, MovieModel, SignInModel, UserModel } from './server.model';
import { environment } from '../../environments/environment';


@Injectable()
export class ServerService {
  private domain: string = environment.W3_API_URL;

  constructor(private httpClient: HttpClient) {}

  getMovieByGroupName(groupName: string): Observable<MovieModel[]> {
    const url: string = this.domain +  '/api/movie/by-group-name';
    const params: HttpParams = new HttpParams().set('groupName', groupName);
    return this.httpClient.get<MovieModel[]>(url, {withCredentials: true, params: params});
  }

  getMovieGroupName(date: string): Observable<MovieGroupNameModel> {
    const url: string = this.domain +  '/api/movie-group';
    const params: HttpParams = new HttpParams().set('date', date);
    return this.httpClient.get<MovieGroupNameModel>(url, {withCredentials: true, params: params});
  }

  getMoviePosterUrl(imdbId: string) {
    return this.domain + '/api/movie-poster/' + imdbId;
  }

  getTop100Movies(): Observable<MovieModel[]> {
    const url: string = this.domain +  '/api/movie/top-100-movies';
    return this.httpClient.get<MovieModel[]>(url, {withCredentials: true});
  }

  getLandingPageMovies(): Observable<MovieModel[]>{
    const url: string = this.domain +  '/api/movie/random-nine-movies';
    return this.httpClient.get<MovieModel[]>(url, {withCredentials: true});
  }

  getGitStatus() {
    const url: string = this.domain +  '/api/git-status';
    return this.httpClient.get(url, {withCredentials: true});
  }

  getRandomUser() {
    const url: string = this.domain +  '/api/user/random';
    return this.httpClient.get(url, {withCredentials: true});
  }

  register(user: UserModel) {
    const url: string = this.domain +  '/api/user/auth/signup';
    return this.httpClient.post(url, user,{withCredentials: true});
  }

  me() {
    const url: string = this.domain +  '/api/user/auth/me';
    return this.httpClient.get(url, {withCredentials: true});
  }

  login(user: SignInModel) {
    const url: string = this.domain +  '/api/user/auth/signin';
    return this.httpClient.post(url, user,{withCredentials: true});
  }

  logout() {
    const url: string = this.domain +  '/api/user/auth/signout';
    // return this.httpClient.get(url,{withCredentials: true});
    return this.httpClient.post(url, {}, {withCredentials: true});
  }
}
