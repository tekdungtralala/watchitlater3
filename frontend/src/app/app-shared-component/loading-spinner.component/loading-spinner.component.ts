import { Component, OnInit } from '@angular/core';
import { SpinnerStateModel, SpinnerService } from './spinner.service';

import * as _ from 'lodash';

@Component({
  selector: 'app-loading-spinner',
  templateUrl: './loading-spinner.component.html',
  styleUrls: ['./loading-spinner.component.css']
})
export class LoadingSpinnerComponent implements OnInit {
  isShown: boolean = false;
  spinnerState: SpinnerStateModel[] = [];

  constructor(private spinnerService: SpinnerService) {
  }

  ngOnInit() {
    this.spinnerService.spinnerActivated.subscribe(
      (value: SpinnerStateModel) => {
        if ( !value.isShown ) {
          _.remove(this.spinnerState, (state: SpinnerStateModel) => {
            return state.key === value.key;
          });
        } else if ( value.isShown ) {
          this.spinnerState.push(value);
        }

        const finded: SpinnerStateModel = _.find(this.spinnerState, (state: SpinnerStateModel) => {
          return state.isShown === true;
        });
        this.isShown = finded ? true : false;
      }
    );
  }
}
