import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  constructor(private router: Router) {
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

    // Activate scrollspy to add active class to navbar items on scroll
    // $('body').scrollspy({ target: '#mainNav', offset: 48});

    // Collapse the navbar when page is scrolled
    // $(window).scroll(function() {
    //   if ($("#mainNav").offset().top > 100) { $("#mainNav").addClass("navbar-shrink");
    //   } else { $("#mainNav").removeClass("navbar-shrink"); }
    // });

    // Scroll reveal calls
    let sr = ScrollReveal();
    // sr.reveal('.sr-icons', { duration: 600, scale: 0.3, distance: '0px'}, 200);
    // sr.reveal('.sr-button', { duration: 1000, delay: 200 });
    // sr.reveal('.sr-contact', { duration: 600, scale: 0.3, distance: '0px'}, 300);
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
