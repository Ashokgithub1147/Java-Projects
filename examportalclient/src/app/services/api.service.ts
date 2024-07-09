import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http:HttpClient) { }
  get(url: string) {
    return this.http.get(url);
}

get2(url: string, options:any) {
    return this.http.get(url, options);
}

post(url: string, postData: Object) {
    return this.http.post(url, postData);
}

post2(url: string, postData: Object, options: any) {
    return this.http.post(url, postData, options);
}

put(url: string, postData: Object) {
    return this.http.put(url, postData);
}

put2(url: string, postData: any, options: any) {
    return this.http.put(url, postData, options);
}

patch(url: string, postData: Object) {
    return this.http.patch(url, postData);
}

delete(url: string) {
    return this.http.delete(url);
}
}
