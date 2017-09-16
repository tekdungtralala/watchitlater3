import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/Rx'

@Injectable()
export class ServerService {
  domain: string = 'http://localhost:8080';

  constructor(private http: Http) {}

  getGitStatus() {
    const url: string = this.domain +  '/api/git-status?' + this.getAuthQueryParam();
    return this.http.get(url);
  }

  login() {
    const url: string = this.domain +  '/api/auth/signin';
    return this.http.post(url, {}).map(
      (response: Response) => {
        const data = response.json();
        return data;
      }
    );
  }

  logout() {
    const url: string = this.domain +  '/api/auth/signout';
    return this.http.get(url);
  }

  getAuthQueryParam() {
    return '&query-param-auth=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjpcInVzZXJcIixcImVtYWlsQWRkcmVzc1wiOlwid2l3aXQuYWRpdHlhLnNhcHV0cmFAZ21haWwuY29tXCIsXCJjcmVhdGVkRGF0ZVwiOlwiU2VwIDE1LCAyMDE3IDY6MDY6NDIgUE1cIn0ifQ.PLv_76zL0Ai-RVSaeORg8c0c_jf67_ynPBitGMKDTwm8lPKwUM1UGfgrekRIRVa6H_VS3Nxz2b0VLtM09c4hCg';
  }
}
