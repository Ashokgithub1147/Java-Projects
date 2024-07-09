import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ExamPortalService } from 'src/app/services/exam-portal.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit{
  userDetailsForm!:FormGroup;
  roleIds= [2]
  constructor(private fb:FormBuilder,
    private examPortalService:ExamPortalService,
    private notificationService:NotificationService){
   
  }
  ngOnInit(): void {
    // this.userDetailsForm.updateValueAndValidity();
    this.formInit()
    // this.clearFormValidators(this.userDetailsForm);
    throw new Error('Method not implemented.');
    
  }
  // clearFormValidators(form:FormGroup) {
  //   Object.keys(form.controls).forEach(key => {
  //     const control = form.get(key);
  //     control?.clearValidators();
  //     control?.updateValueAndValidity();
  //   });
  // }
  formInit(){
    this.userDetailsForm = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      roleIds:[this.roleIds]
    });
  }
  onSignUp(){
    let data = this.userDetailsForm.getRawValue();
    console.log('userData ',data);
    debugger
    this.examPortalService.registerUser(data)?.subscribe(res=>{
      if(res?.status=='OK'){
        this.notificationService.notify(res?.message,this.notificationService.SUCCESS);
        console.log(res?.data);
      }
    })
  }
}
