import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private http: HttpClient, private keycloakService: KeycloakService) { }

  logout() {
    this.keycloakService.clearToken();
    this.keycloakService.logout("http://localhost:4200/");
  }

  userData() {
    console.log(this.keycloakService.isUserInRole("ROLE_USER"));
    console.log(this.keycloakService.getUserRoles());
    console.log(this.keycloakService.loadUserProfile().then(console.log));
  }

}
