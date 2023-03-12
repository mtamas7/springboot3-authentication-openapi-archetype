import {Injectable} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(private snackBar: MatSnackBar) {
  }

  showErrorSnackBarWithMessage(message: string) {
    this.snackBar.open(message, "Close", {
      duration: 2000,
      panelClass: ["snackbar-error"],
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }

  showSuccessSnackBarWithMessage(message: string) {
    this.snackBar.open(message, "Close", {
      duration: 2000,
      panelClass: ["snackbar-success"],
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }
}