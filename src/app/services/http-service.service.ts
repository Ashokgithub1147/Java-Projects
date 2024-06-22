import { Injectable } from '@angular/core';
import { AppConstants } from '../config/app.constants';
import { Observable } from 'rxjs';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  moduleUrl!:string;
  constructor(private appConstants:AppConstants,
    private httpClient:HttpClient,
    private apiService:ApiService) {

    this.moduleUrl = this.appConstants.getConstants().examPortalUrl;
  }

  send(apiUrl:string,httpMethod?:string,payload?:any,headersObject?:any,responseType?:string){
    if(httpMethod=='get')
      return this.getData(apiUrl);
    else if(httpMethod=='post')
      return this.postData(apiUrl,payload);
    else if(httpMethod=='post' && headersObject!=null)
      return this.postData1(apiUrl,payload,headersObject);
    else if(httpMethod=='put')
      return this.putData(apiUrl,payload);
    else if(httpMethod=='put' && headersObject!=null)
      return this.putData1(apiUrl,payload,headersObject);
    else if(apiUrl=='delete')
      return this.deleteData(apiUrl);
    else
      return null;
  }

  getData(apiUrl:string):Observable<any>{
    return this.apiService.get(`${apiUrl}`);
  }
  postData(apiUrl:string,payload?:Object):Observable<any>{
    return this.apiService.post(`${apiUrl}`,payload);
  }
  postData1(apiUrl:string,payload:Object,options:Object):Observable<any>{
    return this.apiService.post2(`${apiUrl}`,payload,options);
  }
  putData(apiUrl:string,payload?:Object):Observable<any>{
    return this.apiService.put(`${apiUrl}`,payload);
  }
  putData1(apiUrl:string,payload:Object,options:Object):Observable<any>{
    return this.apiService.put2(`${apiUrl}`,payload,options);
  }
  patchData(apiUrl:string,payload?:Object):Observable<any>{
    return this.apiService.put(`${apiUrl}`,payload);
  }
  patchData1(apiUrl:string,payload:Object,options:Object):Observable<any>{
    return this.apiService.put2(`${apiUrl}`,payload,options);
  }
  deleteData(apiUrl:string):Observable<any>{
    return this.apiService.delete(apiUrl);
  }
}
