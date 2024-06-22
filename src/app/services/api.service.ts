import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient:HttpClient) { }
  get(apiUrl:string):Observable<any>{
    return this.httpClient.get(apiUrl);
  }
  post(apiUrl:string,payload?:Object):Observable<any>{
    return this.httpClient.post(apiUrl,payload);
  }
  post2(url: string, postData: Object, options: any) {
    return this.httpClient.post(url, postData, options);
  }
  put(apiUrl:string,payload?:Object):Observable<any>{
    return this.httpClient.put(apiUrl,payload);
  }
  put2(url: string, postData: Object, options: any) {
    return this.httpClient.put(url, postData, options);
  }
  patch(apiUrl:string,payload?:Object):Observable<any>{
    return this.httpClient.patch(apiUrl,payload);
  }
  delete(apiUrl:string):Observable<any>{
    return this.httpClient.delete(apiUrl);
  }

}
