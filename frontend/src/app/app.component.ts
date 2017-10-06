import {Component, HostListener, Inject, OnInit} from '@angular/core';
import { DOCUMENT } from '@angular/platform-browser';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  isHeaderShrink: boolean = false;

  @HostListener('window:scroll', []) onScrollEvent() {
    const number = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    this.isHeaderShrink = number > 100 ? true: false;
  }

  constructor(@Inject(DOCUMENT) private document: Document, private router: Router) {
  }

  ngOnInit() {
    console.log('start');

    // Smooth scrolling using jQuery easing
    // $('a.js-scroll-trigger[href*="#"]:not([href="#"])').click(function() {
    //   if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
    //     var target = $(this.hash);
    //     target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
    //     if (target.length) {
    //       $('html, body').animate({
    //         scrollTop: (target.offset().top - 48)
    //       }, 1000, "easeInOutExpo");
    //       return false;
    //     }
    //   }
    // });

    // Closes responsive menu when a scroll trigger link is clicked
    // $('.js-scroll-trigger').click(function() {
    //   $('.navbar-collapse').collapse('hide');
    // });
  }

  onLoadHome() {
    console.log('onLoadHome');
    this.router.navigate(['/']);
  }

  onLoadTop100() {
    console.log('onLoadTop100');
    this.router.navigate(['/top100']);
  }

  onLoadLatest() {
    console.log('onLoadLatest');
    this.router.navigate(['/latest']);
  }

  onLoadRegister() {
    console.log('onLoadRegister');
    this.router.navigate(['/register']);
  }

  onLoadLogin() {
    console.log('onLoadLogin');
    this.router.navigate(['/login']);
  }
}
