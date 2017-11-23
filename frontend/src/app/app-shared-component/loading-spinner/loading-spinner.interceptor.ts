import { Injectable,} from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/finally';

import { LoadingSpinnerService } from './loading-spinner.service';
import { LoadingSpinnerModel } from './loading-spinner.model';

@Injectable()
export class LoadingSpinnerInterceptor implements HttpInterceptor {

  constructor(private spinnerService: LoadingSpinnerService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.spinnerService.spinnerActivated.next(new LoadingSpinnerModel(request.urlWithParams, true));
    return next.handle(request).finally(() => {
      this.spinnerService.spinnerActivated.next(new LoadingSpinnerModel(request.urlWithParams, false));
    });
  }
}
