import { Injectable } from "@angular/core";

@Injectable()
export class AppConstants{
    private constants = {
        examPortalUrl : 'https://localhost:9001',
        examPortalModule:'examportal',
    }

    getConstants(){
        return this.constants;
    }
}