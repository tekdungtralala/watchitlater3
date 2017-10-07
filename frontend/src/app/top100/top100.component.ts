import { Component, OnInit } from '@angular/core';
import { ServerService} from '../app-util/server.service';
import {MovieModel} from "../app-util/movie.model";

@Component({
  selector: 'app-top100',
  templateUrl: './top100.component.html',
  styleUrls: ['./top100.component.css']
})
export class Top100Component implements OnInit {

  top100Movies: MovieModel[];

  constructor(private serverService: ServerService) {
  }

  ngOnInit() {
    this.serverService.getTop100Movies().subscribe(
      ( response: MovieModel[])=> {
        response.forEach((movie => {
          movie.imageUrl = this.serverService.getMoviePosterUrl(movie.imdbId);
        }));
        this.top100Movies = response;
      }
    )
  }

}
