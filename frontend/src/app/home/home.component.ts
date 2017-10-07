import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ServerService } from '../app-util/server.service';
import { MovieModel } from '../app-util/movie.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  listMovieModel: MovieModel[];

  constructor(private serverService: ServerService, private router: Router) {

  }

  ngOnInit() {
    this.serverService.getLandingPageMovies().subscribe(
      (response: MovieModel[]) => {
        response.forEach((movie => {
          movie.imageUrl = this.serverService.getMoviePosterUrl(movie.imdbId);
        }));
        this.listMovieModel = response;
      }
    );
  }

  onLoadTop100() {
    this.router.navigate(['/top100']);
  }

  onLoadLatest() {
    this.router.navigate(['/latest']);
  }

}
