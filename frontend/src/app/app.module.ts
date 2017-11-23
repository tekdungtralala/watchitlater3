import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgsRevealModule } from 'ng-scrollreveal';

import { AppComponent } from './app.component';
import { ServerService } from './app-util/server.service';
import { HomeComponent } from './home/home.component';
import { Top100Component } from './top100/top100.component';
import { LatestComponent } from './latest/latest.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';

import { MoviePopupComponent } from './app-shared-component/movie-popup/movie-popup.component';
import { LoadingSpinnerComponent } from './app-shared-component/loading-spinner/loading-spinner.component';
import { LoadingSpinnerService } from './app-shared-component/loading-spinner/loading-spinner.service';
import { LoadingSpinnerInterceptor } from './app-shared-component/loading-spinner/loading-spinner.interceptor';
import {
  NoEmptyValueValidatorDirective
} from './app-shared-component/validator/no-empty-value-validator/no-empty-value-validator.directive';
import { AuthGuard } from './app-util/auth-guard.service';
import { AppScope } from './app.scope.service';
import { DashboardScope } from './dashboard/dashboard.scope.service';
import { MovieInfoComponent } from './app-shared-component/movie-popup/movie-info/movie-info.component';
import { MovieReviewComponent } from './app-shared-component/movie-popup/movie-review/movie-review.component';

const appRoutes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'top100', component: Top100Component, canActivate: [AuthGuard] },
  { path: 'latest', component: LatestComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [AuthGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] }
];

@NgModule({
  declarations: [
    AppComponent,

    HomeComponent,
    Top100Component,
    LatestComponent,
    RegisterComponent,
    LoginComponent,

    MoviePopupComponent,
    LoadingSpinnerComponent,
    NoEmptyValueValidatorDirective,
    DashboardComponent,
    MovieInfoComponent,
    MovieReviewComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    NgbModule.forRoot(),
    NgsRevealModule.forRoot(),
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  entryComponents: [ MoviePopupComponent ],
  providers: [
    ServerService,
    LoadingSpinnerService,
    { provide: HTTP_INTERCEPTORS, useClass: LoadingSpinnerInterceptor, multi: true },
    AuthGuard,
    AppScope,
    DashboardScope
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
