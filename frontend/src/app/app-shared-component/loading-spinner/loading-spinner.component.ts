import { Component, OnInit } from '@angular/core';
import * as _ from 'lodash';

import { LoadingSpinnerService } from './loading-spinner.service';
import { LoadingSpinnerModel } from './loading-spinner.model';

@Component({
  selector: 'app-loading-spinner',
  templateUrl: './loading-spinner.component.html',
  styleUrls: ['./loading-spinner.component.css']
})
export class LoadingSpinnerComponent implements OnInit {
  isShown: boolean = false;
  spinnerState: LoadingSpinnerModel[] = [];

  constructor(private spinnerService: LoadingSpinnerService) {
  }

  ngOnInit() {
    this.spinnerService.spinnerActivated.subscribe(
      (value: LoadingSpinnerModel) => {
        if ( !value.isShown ) {
          _.remove(this.spinnerState, (state: LoadingSpinnerModel) => {
            return state.key === value.key;
          });
        } else if ( value.isShown ) {
          this.spinnerState.push(value);
        }

        const finded: LoadingSpinnerModel = _.find(this.spinnerState, (state: LoadingSpinnerModel) => {
          return state.isShown === true;
        });
        this.isShown = finded ? true : false;
      }
    );
  }
}
