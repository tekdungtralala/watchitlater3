import { Component } from '@angular/core';
import {ServerService} from "./server.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'watch it later';

  constructor(private ss:ServerService) {
    console.log('ssd');
    this.ss.getGitStatus().subscribe(
      (response) => console.log(response)
    );
  }
}
