import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { ServerService } from '../app-util/server.service';
import { UserModel } from '../app-util/server.model';
import { SignInModel } from '../app-util/fe.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @ViewChild('f') loginForm: NgForm;
  userModel: SignInModel;
  errorMsg: string;
  lastEmail: string;
  showSuccessInfo = false;
  showErrorInfo = false;
  emailCopied = false;

  constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.userModel = new UserModel(params['email'], params['fullName'], params['password']);
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.serverService.login(this.userModel).subscribe( () => {
        console.log('success');
      }, () => {
        console.log('error');
      });
    }
  }

}
