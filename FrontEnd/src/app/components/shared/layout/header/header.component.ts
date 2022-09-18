import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    logo = "fa-user";//fa-sign-out-alt

  // search(event:any) {
  //   this.saveCategory.emit(event)
  //   this.productService.searchHeader(event.target.name)
  //   this.router.navigate(["/","products"]).then()
  // }

    @Output() saveCategory = new EventEmitter<any>()

    constructor() {
    }

    ngOnInit(): void {
        if (JSON.parse(localStorage.getItem("user")!).role == "ADMIN"){
            this.logo = "fa-sign-out-alt"
        } else{
            this.logo = "fa-user"
        }
    }

    // login(){
    //     if(this.logo == "fa-user"){
    //         this.router.navigate(["/login"])
    //     }else{
    //         localStorage.setItem("user", JSON.stringify(this.user))
    //         this.logo = "fa-user";
    //         this.router.navigate([""])
    //     }
    // }
}
