import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs/Observable';

import { ServerService } from '../app-util/server.service';
import { RestException, UserModel } from '../app-util/server.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @ViewChild('f') registerForm: NgForm;
  userModel: UserModel;
  errorMsg: string;
  lastEmail: string;
  showRandomUserInfo = false;
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
      this.lastEmail = this.userModel.email;
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

  onCopyEmail() {
    this.copyToClipboard(this.lastEmail);
    this.emailCopied = true;
    Observable.timer(1000).subscribe(() => {
      this.emailCopied = false;
    });
  }

  onToSignInPage() {
    this.router.navigate(['/login']);
  }

  private updateInfo(random: boolean, success: boolean, error: boolean): void {
    this.showRandomUserInfo = random;
    this.showSuccessInfo = success;
    this.showErrorInfo = error;
  }

  private copyToClipboard(text): void {
    const textarea: any = document.createElement('textarea');
    textarea.textContent = text;
    textarea.style.position = 'fixed';
    document.body.appendChild(textarea);
    textarea.select();
    try {
      document.execCommand('copy');  // Security exception may be thrown by some browsers.
    } catch (ex) {
      console.warn('Copy to clipboard failed.', ex);
    } finally {
      document.body.removeChild(textarea);
    }
  }
}
