import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpService } from 'src/app/services/http-service.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit{

  userDetails!: FormGroup;
  user:string='';
  constructor(private fb: FormBuilder,
    private httpService:HttpService) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    //intializing userDetails form
    this.userDetails = this.fb.group({
      userName: [null, Validators.required],
      password:[null,Validators.required],
      firstName:[null,Validators.required],
      lastName:[null,Validators.required],
      email:[null,Validators.required],
      phone:[null,Validators.required],
    });
  }

  onSignUp(): void {
    let formData = this.userDetails.getRawValue();
    console.log(formData);
    if (this.userDetails.valid) {
      // handle form submission
      // this.httpService.send
    }
  }
}

export interface UserDetails{
  id:number;
	userName:string;
	password:string;
	firstName:string;
	lastName:string;
	email:string;
	phone:string;
	// profileUrl:string;
}
	
