import { Injectable,} from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/finally';

import {SpinnerStateModel, SpinnerService} from './spinner.service';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {

  constructor(private spinnerService: SpinnerService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.spinnerService.spinnerActivated.next(new SpinnerStateModel(request.urlWithParams, true));
    return next.handle(request).finally(() => {
      this.spinnerService.spinnerActivated.next(new SpinnerStateModel(request.urlWithParams, false));
    });
  }
}
