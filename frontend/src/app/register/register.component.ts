import { Component, OnInit } from '@angular/core';
import {ServerService} from '../app-util/server.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private serverService: ServerService) {
    console.log('RegisterComponent');
    serverService.logout().subscribe();
  }

  ngOnInit() {
  }

}
