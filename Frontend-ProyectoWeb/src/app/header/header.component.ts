import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../services/security.service';



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  isLogged: Boolean = false
  constructor(private securityService: SecurityService){
    
  }
   //TOOO: REVISAR VARIABLE ISLOGGED
  ngOnInit(): void {
    this.securityService.isLogged().then(b => this.isLogged = b);
  }
   
  logout(){
    this.securityService.logout();
  }

}
