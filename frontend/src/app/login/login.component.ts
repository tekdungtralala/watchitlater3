import { Component, OnInit } from '@angular/core';
import { ServerService } from '../server.service';
import {Response} from "@angular/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private serverService: ServerService) {
    console.log('login');
    this.serverService.login().subscribe(
      (response: Response) => {
        console.log(response);
      }
    );
  }

  ngOnInit() {
  }

}
