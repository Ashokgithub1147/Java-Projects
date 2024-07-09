  import { Injectable } from '@angular/core';
  import { MatSnackBar,MatSnackBarHorizontalPosition,MatSnackBarVerticalPosition} from '@angular/material/snack-bar';
  import {MatButtonModule} from '@angular/material/button';
  import {MatSelectModule} from '@angular/material/select';
  import {MatFormFieldModule} from '@angular/material/form-field';

  /**
   * @title Snack-bar with configurable position
   */
  @Injectable({
    providedIn: 'root'
  })
  export class NotificationService {
    horizontalPosition: MatSnackBarHorizontalPosition = 'center';
    verticalPosition: MatSnackBarVerticalPosition = 'top';
    durationInSeconds = 5;

    public SUCCESS: string = 'success';
    public WARNING: string = 'warning';
    public ERROR: string = 'error';
    public INFO: string = 'info';
    backColor = '';

    constructor(private _snackBar: MatSnackBar) {}
    notify(message: string, messageType: string) {
      let panelClass: string[] = [];
      if (messageType === this.ERROR) {
        panelClass.push('failure-snackbar');
      } else if (messageType === this.INFO) {
        panelClass.push('info-snackbar');
      } else if (messageType === this.SUCCESS) {
        panelClass.push('success-snackbar');
      } else if (messageType === this.WARNING) {
        panelClass.push('warning-snackbar');
      }
  
      this.openSnackBar(message, panelClass);
    }
  
    openSnackBar(message: string, panelClass: string[]) {
      this._snackBar.open(message, 'Close', {
        horizontalPosition: this.horizontalPosition,
        verticalPosition: this.verticalPosition,
        duration: this.durationInSeconds * 1000,
        panelClass: panelClass
      });
    }
  }
