export class SearchParams {
  searchString?: string = "";
  page?: number = 1;

  constructor(searchString: string = "", page: number = 1) {
    this.searchString = searchString;
    this.page = page;
  }
}
