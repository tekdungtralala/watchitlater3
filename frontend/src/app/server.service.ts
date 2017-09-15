import {Injectable} from "@angular/core";
import {Http} from "@angular/http";

@Injectable()
export class ServerService {
  domain: string = "http://localhost:8080";

  constructor(private http: Http) {}

  getGitStatus() {
    const url: string = this.domain +  "/api/git-status";
    return this.http.get(url);
  }

  login() {
    const url: string = this.domain +  "/api/auth/signin";
    return this.http.get(url, {withCredentials: true});
  }

  logout() {
    const url: string = this.domain +  "/api/auth/signout";
    return this.http.get(url);
  }
}
