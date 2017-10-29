import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { NgForm } from '@angular/forms';

import { ServerService } from '../app-util/server.service';
import { UserModel } from '../app-util/server.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @ViewChild('f') registerForm: NgForm;
  userModel: UserModel;
  showRandomUserInfo = false;
  showSuccessInfo = false;

  constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.userModel = new UserModel(params['emailAddress'], params['fullName'], params['password']);
      console.log(this.userModel)
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
      this.showRandomUserInfo = false;
      this.showSuccessInfo = true;
      this.userModel.reset();
      this.registerForm.resetForm();
    });

  }

  onRandomUser() {
    this.serverService.getRandomUser().subscribe((randomUser: UserModel) => {
      this.showRandomUserInfo = true;
      this.showSuccessInfo = false;
      this.userModel = new UserModel(randomUser.email, randomUser.fullName, 'password');
    });
  }

}
