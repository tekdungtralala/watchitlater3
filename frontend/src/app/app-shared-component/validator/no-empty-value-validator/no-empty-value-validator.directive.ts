import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from '@angular/forms';

@Directive({
  selector: '[noEmptyValue][ngModel]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: NoEmptyValueValidatorDirective, multi: true }
  ]
})
export class NoEmptyValueValidatorDirective implements Validator {
  validate(c: AbstractControl) {
    if (!c.value || c.value.trim() === '') {
      return {
        emptyString: { valid: true }
      };
    } else {
      return null;
    }
  }
}
