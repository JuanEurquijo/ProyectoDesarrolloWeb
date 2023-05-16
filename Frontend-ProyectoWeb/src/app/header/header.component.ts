import { Component } from '@angular/core';
import { SecurityService } from '../services/security.service';



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {


  constructor(private securityService: SecurityService){
    
  }

  logout(){
    this.securityService.logout();
  }

}
