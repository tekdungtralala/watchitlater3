import { Component, OnInit } from '@angular/core';
import { ServerService } from '../app-util/server.service';

@Component({
  selector: 'app-latest',
  templateUrl: './latest.component.html',
  styleUrls: ['./latest.component.css']
})
export class LatestComponent implements OnInit {

  constructor(private serverService: ServerService) {
    console.log('LatestComponent');
    serverService.me().subscribe();
  }

  ngOnInit() {
  }

}
