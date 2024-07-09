import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { AppConstants } from '../config/AppConstants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExamPortalService {

  private moduleUrl: string = '';
  private moduleName: string = '';
  constructor(private httpService:HttpService,private appConstants:AppConstants) {
    this.moduleUrl =  this.appConstants.getConstants().examPortalBaseUrl;

   }

  registerUser(payload:any){
    let url = `${this.moduleUrl}/examportal/user/createUser`;
    return this.httpService.send(url,'post',payload)?.pipe(response=>response);
  }
}
