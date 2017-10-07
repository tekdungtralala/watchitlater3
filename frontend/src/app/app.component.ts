import {Component, HostListener, Inject, OnInit} from '@angular/core';
import { DOCUMENT } from '@angular/platform-browser';
import { NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private mustShrink: boolean = false;
  isHeaderShrink: boolean = false;
  currentRouter: string = '/';

  @HostListener('window:scroll', []) onScrollEvent() {
    const number = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    this.isHeaderShrink = this.mustShrink || number > 100 ? true: false;
  }

  constructor(@Inject(DOCUMENT) private document: Document, private router: Router) {
  }

  ngOnInit() {
    this.router.events.subscribe(
      (val: any) => {
        this.isHeaderShrink = val.url !== '/';
      }
    );
  }

  onLoadHome() {
    this.mustShrink = false;
    this.currentRouter = '/';
    this.router.navigate(['/']);
  }

  onLoadTop100() {
    this.mustShrink = true;
    this.currentRouter = '/top100';
    this.router.navigate(['/top100']);
  }

  onLoadLatest() {
    this.mustShrink = true;
    this.currentRouter = '/latest';
    this.router.navigate(['/latest']);
  }

  onLoadRegister() {
    this.mustShrink = true;
    this.currentRouter = '/register';
    this.router.navigate(['/register']);
  }

  onLoadLogin() {
    this.mustShrink = true;
    this.currentRouter = '/login';
    this.router.navigate(['/login']);
  }
}
