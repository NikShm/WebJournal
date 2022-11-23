import { Directive } from '@angular/core';
import { Validator, NG_VALIDATORS, ValidatorFn, FormControl, AbstractControl } from '@angular/forms';

@Directive({
  selector: '[appBirthDateValidator]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useClass: BirthDateValidatorDirective,
      multi: true
    }
  ]
})
export class BirthDateValidatorDirective implements Validator {
  validator: ValidatorFn;

  constructor() {
    this.validator = this.birthDateValidator();
  }

  validate(control: FormControl) {
    return this.validator(control);
  }

  birthDateValidator(): ValidatorFn {
    return (control: AbstractControl) => {
      const birthDate: any = control.value;
      if (birthDate) {
        const diff = new Date(new Date().getTime() - new Date(birthDate).getTime()).getFullYear() - 1970;
        if (diff >= 18) {
          return null;
        }
        else {
          return {
            birthDate: { valid: false }
          };
        }
      }
      else {
        return null;
      }
    }
  }
}
