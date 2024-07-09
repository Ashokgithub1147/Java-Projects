import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  constructor(private api:ApiService) {
   }
  send(apiMethod: string, method: string, payload?: any, headersObject?: Object, responseType?: string) {
    if (method == 'get')
      return this.getData(apiMethod);
    else if (method == 'post')
      return this.postData(apiMethod, payload);
    else if (method == 'put')
      return this.putData(apiMethod, payload);
    else if (method == 'delete')
      return this.deleteData(apiMethod);
    else
      return null;
  }

  getData(apiMethod: string): Observable<any> {
    return this.api.get(`${apiMethod}`);
  }

  postData(apiMethod: string, payload: any): Observable<any> {
    return this.api.post(`${apiMethod}`, payload);
  }

  putData(apiMethod: string, payload: any = null): Observable<any> {
    return this.api.put(`${apiMethod}`, payload);
  }

  deleteData(apiMethod: string): Observable<any> {
    return this.api.delete(`${apiMethod}`);
  }
}
