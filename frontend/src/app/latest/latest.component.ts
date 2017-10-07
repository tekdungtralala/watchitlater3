import { Component, OnInit } from '@angular/core';
import { ServerService } from '../app-util/server.service';

import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-latest',
  templateUrl: './latest.component.html',
  styleUrls: ['./latest.component.css']
})
export class LatestComponent implements OnInit {
  model: NgbDateStruct;
  date: {year: number, month: number};

  constructor(private serverService: ServerService) {
  }

  ngOnInit() {
  }

}
