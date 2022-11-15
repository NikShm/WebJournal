export class Search {
    constructor(sortField: string, sortDirection: string, page: number, pageSize: number, searchPattern : any) {
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.page = page;
        this.pageSize = pageSize;
        this.searchPattern = searchPattern;
    }

    sortField : string;
    sortDirection : string;
    page : number;
    pageSize : number;
    searchPattern : any;
}
