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
import { MovieDetailComponent } from './app-shared-component/movie-detail.component/movie-detail.component';
import { LoadingSpinnerComponent } from './app-shared-component/loading-spinner.component/loading-spinner.component';
import { LoadingSpinnerService } from './app-shared-component/loading-spinner.component/loading-spinner.service';
import { LoadingSpinnerInterceptor } from './app-shared-component/loading-spinner.component/loading-spinner.interceptor';
import { NoEmptyValueValidatorDirective } from './app-shared-component/validator/no-empty-value-validator/no-empty-value-validator.directive';

const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'top100', component: Top100Component },
  { path: 'latest', component: LatestComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  declarations: [
    AppComponent,

    HomeComponent,
    Top100Component,
    LatestComponent,
    RegisterComponent,
    LoginComponent,

    MovieDetailComponent,
    LoadingSpinnerComponent,
    NoEmptyValueValidatorDirective
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
  entryComponents: [ MovieDetailComponent ],
  providers: [
    ServerService,
    LoadingSpinnerService,
    { provide: HTTP_INTERCEPTORS, useClass: LoadingSpinnerInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
