import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../services/security.service';



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  isLoggedIn$: Promise<boolean>;

  constructor(private securityService: SecurityService){

  }

  ngOnInit(): void {
    this.isLoggedIn$ = this.securityService.isLogged();
  }


  logout(){
    this.securityService.logout();
  }

}
