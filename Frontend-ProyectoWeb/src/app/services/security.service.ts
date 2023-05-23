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

  isUserInRole(role:string) {
    return this.keycloakService.isUserInRole(role);
  }

  isLogged() {
    return this.keycloakService.isLoggedIn();
  }

}
