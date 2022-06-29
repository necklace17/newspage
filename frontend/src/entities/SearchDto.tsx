export class SearchDto {
  searchString?: string = "";

  constructor(searchString: string = "") {
    this.searchString = searchString;
  }
}
