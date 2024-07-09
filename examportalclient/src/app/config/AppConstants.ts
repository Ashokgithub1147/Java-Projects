import { Injectable } from '@angular/core';
@Injectable()
export class AppConstants {
    private constants = {
        examPortalBaseUrl:'http://localhost:9001'
    };
      
    
    getConstants(){
        return this.constants;
    }
}
