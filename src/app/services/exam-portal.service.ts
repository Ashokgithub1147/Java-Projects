import { Injectable } from '@angular/core';
import { HttpService } from './http-service.service';
import { AppConstants } from '../config/app.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExamPortalService {
  moduleUrl!:string;
  moduleName!:string;
  constructor(private httpService:HttpService,
    private appConstants:AppConstants,
  ) {
      this.moduleUrl = this.appConstants.getConstants().examPortalUrl;
      this.moduleName = this.appConstants.getConstants().examPortalModule;
   }

   saveUserDetails(payload:Object){
    let url=`${this.moduleUrl}${this.moduleName}/user/create-user`;
    return this.httpService.send(url,'post',payload,null)?.pipe(response=>response);
   }
}
