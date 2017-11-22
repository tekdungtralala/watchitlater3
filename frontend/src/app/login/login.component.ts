import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {ActivatedRoute, Params, Router} from '@angular/router';

import {ServerService} from '../app-util/server.service';
import {RestException, SignInModel, UserModel} from '../app-util/server.model';
import {AppScope} from '../app.scope.service';

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

  constructor(private serverService: ServerService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private rootScope: AppScope) {
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      this.userModel = new UserModel(params['email'], params['fullName'], params['password']);
    });
  }

  onSubmit() {
    this.showErrorInfo = false;
    if (this.loginForm.valid) {
      this.serverService.login(this.userModel).subscribe((loggedUser: UserModel) => {
        this.rootScope.setUser(loggedUser);
        this.router.navigate(['/dashboard']);
      }, (error: RestException) => {
        this.errorMsg = error.error.message;
        this.showErrorInfo = true;
        this.rootScope.setUser(null);
      });
    }
  }

}
