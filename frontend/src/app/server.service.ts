import {Injectable} from "@angular/core";
import {Http} from "@angular/http";

@Injectable()
export class ServerService {
  constructor(private http: Http) {}

  getGitStatus() {
    const url: string = "http://localhost:8080/api/git-status";
    return this.http.get(url);
  }
}
