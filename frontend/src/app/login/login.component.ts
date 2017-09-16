import { Component, OnInit } from '@angular/core';
import { ServerService } from '../app-util/server.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private serverService: ServerService) {
    console.log('login');
    this.serverService.login().subscribe(
      (response: any) => {
        console.log(response);
      }
    );
  }

  ngOnInit() {
  }

}
