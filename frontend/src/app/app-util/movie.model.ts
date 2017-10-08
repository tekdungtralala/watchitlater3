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
}

export class MovieGroupNameModel {
  available: boolean;
  firstDayOfWeek: string;
  groupName: string;
  lastDayOfWeek: string;
}
