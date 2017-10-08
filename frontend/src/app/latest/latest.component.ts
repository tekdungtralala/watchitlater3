import { Component, OnInit } from '@angular/core';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';

import { ServerService } from '../app-util/server.service';


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
    const m: moment.Moment = moment(new Date());
    const currentDate = m.format('YYYY-MM-DD');
    this.serverService.getMovieGroupName(currentDate).subscribe(
      (value) => {
        console.log(value);
      }
    );
  }

}
