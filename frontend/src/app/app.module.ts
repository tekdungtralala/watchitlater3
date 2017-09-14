import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from "@angular/http";
import {RouterModule, Routes} from "@angular/router";

import { AppComponent } from './app.component';
import { ServerService } from "./server.service";
import { HomeComponent } from './home/home.component';
import { Top100Component } from './top100/top100.component';
import { LatestComponent } from './latest/latest.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

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
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [ServerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
