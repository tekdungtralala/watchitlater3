import { Component } from '@angular/core';
import {ServerService} from "./server.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(private ss:ServerService) {
    console.log('ss');
    this.ss.getGitStatus().subscribe(
      (response) => console.log(response)
    );
  }
}
