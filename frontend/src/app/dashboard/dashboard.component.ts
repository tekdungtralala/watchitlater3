import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as _ from 'lodash';

import { ServerService } from '../app-util/server.service';
import { RootScopeService } from '../app-util/root-scope.service';
import {MovieFavoriteModel, MovieModel} from '../app-util/server.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private serverService: ServerService, private router: Router, private rootScope: RootScopeService) {
  }

  ngOnInit() {
    // this.serverService.getMovieFavorite().subscribe((response: MovieFavoriteModel[]) => {
    //   const movieIds: number[] = [];
    //   _.forEach(response, (m: MovieFavoriteModel) => {
    //     movieIds.push(m.movieId);
    //   });
    //
    //   this.serverService.getMovieByMovieIds(movieIds).subscribe((result: MovieModel[]) => {
    //     this.rootScope.setFavoriteMovie(result);
    //   });
    // });

    console.log(this.rootScope.getFavoriteMovie())
  }

  onSignOut() {
    this.serverService.logout().subscribe(() => {
      this.router.navigate(['/']);
    }, () => {
      this.router.navigate(['/']);
    });
  }

}
