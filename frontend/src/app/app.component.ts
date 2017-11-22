import {Component, HostListener, Inject, OnInit} from '@angular/core';
import {DOCUMENT} from '@angular/platform-browser';
import {NavigationEnd, Router} from '@angular/router';
import {AppScope} from './app.scope.service';
import {RootScopeKey, RootScopeModel} from './app-util/fe.model';
import {UserModel} from './app-util/server.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private mustShrink: boolean;
  isHeaderShrink: boolean;
  currentRouter: string;
  hasUser: boolean;

  @HostListener('window:scroll', [])
  onScrollEvent() {
    const number = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    this.isHeaderShrink = this.mustShrink || number > 100 ? true : false;
  }

  constructor(private router: Router, private rootScope: AppScope) {
    this.mustShrink = false;
    this.isHeaderShrink = false;
    this.currentRouter = '/';
  }

  ngOnInit() {
    this.router.events.subscribe(
      (val: any) => {
        this.isHeaderShrink = val.url !== '/';
        this.mustShrink = val.url !== '/';
        this.currentRouter = val.url;
      }
    );

    this.rootScope.getScope().subscribe((model: RootScopeModel) => {
      if (model.key === RootScopeKey.HAS_USER) {
        const loggedUser: UserModel = <UserModel> model.value;
        this.hasUser = loggedUser != null;
      }
    });
  }

  onLoadHome() {
    this.mustShrink = false;
    this.currentRouter = '/';
    this.router.navigate(['/']);
  }

  onLoadTop100() {
    this.router.navigate(['/top100']);
  }

  onLoadLatest() {
    this.router.navigate(['/latest']);
  }

  onLoadRegister() {
    this.router.navigate(['/register']);
  }

  onLoadLogin() {
    this.router.navigate(['/login']);
  }

  onDashboard() {
    this.router.navigate(['/dashboard']);
  }
}
