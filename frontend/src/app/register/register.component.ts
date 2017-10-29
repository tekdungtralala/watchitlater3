import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { NgForm } from '@angular/forms';

import { ServerService } from '../app-util/server.service';
import {RestException, UserModel} from '../app-util/server.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @ViewChild('f') registerForm: NgForm;
  userModel: UserModel;
  errorMsg: string;
  showRandomUserInfo = false;
  showSuccessInfo = false;
  showErrorInfo = false;

  constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.userModel = new UserModel(params['emailAddress'], params['fullName'], params['password']);
    });
  }

  onSubmit() {
    if (!this.registerForm.valid) {
      return;
    }

    if (this.userModel.password !== this.userModel.rePassword) {
      this.registerForm.controls['password'].setErrors({});
      this.registerForm.controls['rePassword'].setErrors({});
      return;
    }

    this.serverService.register(this.userModel).subscribe(() => {
      this.updateInfo(false, true, false);
      this.userModel.reset();
      this.registerForm.resetForm();
    }, (error: RestException) => {
      this.errorMsg = error.error.message;
      this.updateInfo(false, false, true);
    });
  }

  onRandomUser() {
    this.serverService.getRandomUser().subscribe((randomUser: UserModel) => {
      this.updateInfo(true, false, false);
      this.userModel = new UserModel(randomUser.email, randomUser.fullName, 'password');
    });
  }

  private updateInfo(random: boolean, success: boolean, error: boolean): void {
    this.showRandomUserInfo = random;
    this.showSuccessInfo = success;
    this.showErrorInfo = error;
  }
}
