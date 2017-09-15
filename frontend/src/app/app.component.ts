import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private router: Router) {
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
