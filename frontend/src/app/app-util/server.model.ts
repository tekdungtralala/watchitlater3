export class SignInModel {
  email: string;
  password: string;
}

export class UserModel {
  userId: string;
  email: string;
  fullName: string;
  password: string;
  rePassword: string;

  constructor(email: string, fullName: string, password: string) {
    this.email = email;
    this.fullName = fullName;
    this.password = password;
    this.rePassword = password;
  }

  reset = () => {
    this.userId = null;
    this.email = null;
    this.fullName = null;
    this.password = null;
    this.rePassword = null;
  }
}

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

export class RestException {
  error: {
    timestamp: number;
    status: number;
    error: string;
    exception: string;
    message: string;
    path: string;
  }
}
