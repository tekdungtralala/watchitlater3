import { Component, OnInit } from '@angular/core';
import { NgbModal, ModalDismissReasons, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';


import { ServerService} from '../app-util/server.service';
import { MovieModel } from '../app-util/server.model';

@Component({
  selector: 'app-top100',
  templateUrl: './top100.component.html',
  styleUrls: ['./top100.component.css']
})
export class Top100Component implements OnInit {

  top100Movies: MovieModel[];
  selectedMovie: MovieModel;

  constructor(private serverService: ServerService,
              private modalService: NgbModal) {
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

  open(content, movie: MovieModel) {
    this.selectedMovie = movie;
    const options: NgbModalOptions = {
      backdrop: 'static',
      size: 'lg'
    };
    this.modalService.open(content, options);
  }

}
