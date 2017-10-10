export class MovieModelRating {
  Source: string;
  Value: string;
}
export class MovieModel {
  title: string;
  genre: string;
  imdbId: string;
  imdbRating: number;
  plot: string;
  released: string;
  year: number;
  json: string;

  imageUrl: string;
  jsonObj: {
    Title: string;
    Genre: string;
    Language: string;
    Released: string;

    Director: string;
    Writer: string;
    Actors: string;
    Country: string;

    Runtime: string;
    Year: string;
    imdbID: string;
    imdbVotes: string;
    imdbRating: string;

    Awards: string;
    Ratings: MovieModelRating[];
    Metascore: string;

    plot: string;

    Type: string;
    DVD: string;
    BoxOffice: string;
    Production: string;
    Website: string;
    Response: boolean;
  }
}

export class MovieGroupNameModel {
  available: boolean;
  firstDayOfWeek: string;
  groupName: string;
  lastDayOfWeek: string;
}
