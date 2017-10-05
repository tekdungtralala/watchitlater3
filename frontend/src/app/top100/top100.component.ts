import { Component, OnInit } from '@angular/core';
import { ServerService} from '../app-util/server.service';

@Component({
  selector: 'app-top100',
  templateUrl: './top100.component.html',
  styleUrls: ['./top100.component.css']
})
export class Top100Component implements OnInit {

  constructor(private serverService: ServerService) {
    console.log('RegisterComponent');
    serverService.logout().subscribe();
  }

  ngOnInit() {
  }

}
