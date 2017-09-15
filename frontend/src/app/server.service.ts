import {Injectable} from "@angular/core";
import {Http} from "@angular/http";

@Injectable()
export class ServerService {
  domain: string = "http://localhost:8080";

  constructor(private http: Http) {}

  getGitStatus() {
    const url: string = this.domain +  "/api/git-status?" + this.getAuthQueryParam();
    return this.http.get(url);
  }

  login() {
    const url: string = this.domain +  "/api/auth/signin?" + this.getAuthQueryParam();
    return this.http.get(url, {withCredentials: true});
  }

  logout() {
    const url: string = this.domain +  "/api/auth/signout?" + this.getAuthQueryParam();
    return this.http.get(url);
  }

  getAuthQueryParam() {
    return '&query-param-auth=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjpcIndpd2l0XCIsXCJlbWFpbEFkZHJlc3NcIjpcInRva2VuXCIsXCJjcmVhdGVkRGF0ZVwiOlwiU2VwIDE1LCAyMDE3IDExOjIwOjQ5IEFNXCJ9In0.Dqv_suXhqOh_vQGHXBzUHPsxvPvxUlTvyhSClmcTyUoDSklvKR8Se0Xp6F6hyMw7w9k5e_Z8YR4mFA3-zs72pg';
  }
}
