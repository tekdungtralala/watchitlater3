import { Component, OnInit } from '@angular/core';
import { ServerService } from '../app-util/server.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private serverService: ServerService) {
    console.log('LoginComponent');
    this.serverService.login().subscribe();
  }

  ngOnInit() {
  }

}
