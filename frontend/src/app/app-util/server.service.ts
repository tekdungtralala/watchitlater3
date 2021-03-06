import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';

import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';

import {
  MovieFavoriteReq, MovieFavoriteModel, MovieGroupNameModel, MovieModel, MovieReviewResp, MovieReviewRequest,
  SignInModel, UserReq,
  UserModel
} from './server.model';
import {environment} from '../../environments/environment';

@Injectable()
export class ServerService {
  private domain: string = environment.W3_API_URL;

  constructor(private httpClient: HttpClient) {
  }

  getOwnMovieReview(movieId: number): Observable<MovieReviewResp> {
    const url: string = this.domain + '/api/movie-review/' + movieId + '/me';
    return this.httpClient.get<MovieReviewResp>(url, {withCredentials: true});
  }

  getMovieReview(movieId: number, offset: number): Observable<MovieReviewResp[]> {
    let qp: string = '?';
    qp += '&offset=' + offset;
    qp += '&movieId=' + movieId;
    const url: string = this.domain + '/api/movie-review' + qp;
    return this.httpClient.get<MovieReviewResp[]>(url, {withCredentials: true});
  }

  postMovieReview(review: string, movieId: number): Observable<any> {
    const url: string = this.domain + '/api/movie-review';
    const data: MovieReviewRequest = {
      movieId: movieId,
      review: review
    };
    return this.httpClient.post<any>(url, data, {withCredentials: true});
  }

  getDomain(): string {
    return this.domain;
  }

  updateMovieFavorite(data: MovieFavoriteReq): void {
    const url: string = this.domain + '/api/movie-favorite';
    this.httpClient.post<MovieModel[]>(url, data, {withCredentials: true}).subscribe();
  }

  getMovieByMovieIds(movieIds: number[]): Observable<MovieModel[]> {
    const url: string = this.domain + '/api/movie/by-movie-ids';
    const data = {movieIds: movieIds};
    return this.httpClient.post<MovieModel[]>(url, data, {withCredentials: true});
  }

  getMovieFavorite(): Observable<MovieFavoriteModel[]> {
    const url: string = this.domain + '/api/movie-favorite';
    return this.httpClient.get<MovieFavoriteModel[]>(url, {withCredentials: true});
  }

  getMovieByGroupName(groupName: string): Observable<MovieModel[]> {
    const url: string = this.domain + '/api/movie/by-group-name';
    const params: HttpParams = new HttpParams().set('groupName', groupName);
    return this.httpClient.get<MovieModel[]>(url, {withCredentials: true, params: params});
  }

  getMovieGroupName(date: string): Observable<MovieGroupNameModel> {
    const url: string = this.domain + '/api/movie-group';
    const params: HttpParams = new HttpParams().set('date', date);
    return this.httpClient.get<MovieGroupNameModel>(url, {withCredentials: true, params: params});
  }

  getMoviePosterUrl(imdbId: string) {
    return this.domain + '/api/movie-poster/' + imdbId;
  }

  getTop100Movies(): Observable<MovieModel[]> {
    const url: string = this.domain + '/api/movie/top-100-movies';
    return this.httpClient.get<MovieModel[]>(url, {withCredentials: true});
  }

  getLandingPageMovies(): Observable<MovieModel[]> {
    const url: string = this.domain + '/api/movie/random-nine-movies';
    return this.httpClient.get<MovieModel[]>(url, {withCredentials: true});
  }

  getGitStatus() {
    const url: string = this.domain + '/api/git-status';
    return this.httpClient.get(url, {withCredentials: true});
  }

  getUserProfilePictureUrl(userId: string): string {
    return this.domain + '/api/user/profile-picture/' + userId;
  }

  getRandomUser() {
    const url: string = this.domain + '/api/user/random';
    return this.httpClient.get(url, {withCredentials: true});
  }

  editUser(data: UserReq) {
    const url: string = this.domain + '/api/user';
    return this.httpClient.put(url, data, {withCredentials: true});
  }

  register(user: UserModel) {
    const url: string = this.domain + '/api/user/auth/signup';
    return this.httpClient.post(url, user, {withCredentials: true});
  }

  me(): Observable<UserModel> {
    const url: string = this.domain + '/api/user/auth/me';
    return this.httpClient.get<UserModel>(url, {withCredentials: true});
  }

  login(user: SignInModel): Observable<UserModel> {
    const url: string = this.domain + '/api/user/auth/signin';
    return this.httpClient.post<UserModel>(url, user, {withCredentials: true});
  }

  logout() {
    const url: string = this.domain + '/api/user/auth/signout';
    return this.httpClient.post(url, {}, {withCredentials: true});
  }
}
