import { Component, OnInit } from '@angular/core';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';

import { ServerService } from '../app-util/server.service';
import { MovieGroupNameModel } from '../app-util/movie.model';

const equals = (one: NgbDateStruct, two: NgbDateStruct) =>
  one && two && two.year === one.year && two.month === one.month && two.day === one.day;

const before = (one: NgbDateStruct, two: NgbDateStruct) =>
  !one || !two ? false : one.year === two.year ? one.month === two.month ? one.day === two.day
    ? false : one.day < two.day : one.month < two.month : one.year < two.year;

const after = (one: NgbDateStruct, two: NgbDateStruct) =>
  !one || !two ? false : one.year === two.year ? one.month === two.month ? one.day === two.day
    ? false : one.day > two.day : one.month > two.month : one.year > two.year;

@Component({
  selector: 'app-latest',
  templateUrl: './latest.component.html',
  styleUrls: ['./latest.component.css']
})
export class LatestComponent implements OnInit {
  currentDate: NgbDateStruct;
  selectedDate: NgbDateStruct;
  fromDate: NgbDateStruct;
  toDate: NgbDateStruct;

  constructor(private serverService: ServerService) {
  }

  ngOnInit() {
    // const m: moment.Moment = moment(new Date());
    // const currentDate = m.format('YYYY-MM-DD');
    const now = new Date();
    this.selectedDate = {
      year: now.getFullYear(),
      month: now.getMonth() + 1,
      day: now.getDate()
    };
    this.currentDate = this.selectedDate;
  }

  onDateChanged() {
    if (after(this.currentDate, this.selectedDate) || equals(this.currentDate, this.selectedDate)) {
      const date: string = this.selectedDate.year + '-' + this.selectedDate.month + '-' + this.selectedDate.day;
      this.serverService.getMovieGroupName(date).subscribe(
        (value: MovieGroupNameModel) => {
          const fdow: string[] = value.firstDayOfWeek.split('-');
          const ldow: string[] = value.lastDayOfWeek.split('-');
          this.fromDate = {
            year: +fdow[0],
            month: +fdow[1],
            day: +fdow[2]
          }
          this.toDate = {
            year: +ldow[0],
            month: +ldow[1],
            day: +ldow[2]
          }
        }
      );
    }
  }


  isInside = date => after(date, this.fromDate) && before(date, this.toDate);
  isFrom = date => equals(date, this.fromDate);
  isTo = date => equals(date, this.toDate);

}
