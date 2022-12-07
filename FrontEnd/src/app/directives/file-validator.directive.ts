import { Directive } from '@angular/core';
import { Validator, NG_VALIDATORS, ValidatorFn, FormControl, AbstractControl } from '@angular/forms';

@Directive({
  selector: '[appFileValidator]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useClass: FileValidatorDirective,
      multi: true
    }
  ]
})
export class FileValidatorDirective implements Validator {
  validator: ValidatorFn;
  acceptedExts: string[] = ['.jpg'];

  constructor() {
    this.validator = this.fileValidator();
  }

  validate(c: FormControl) {
    return this.validator(c);
  }

   fileValidator(): ValidatorFn {
    return (control: AbstractControl) => {
      const fileName = control.value;
      if (fileName) {
        const matches = fileName.match(/.[a-zA-Z]+/g);
        const extension = matches[matches.length-1];
        if (this.acceptedExts.includes(extension)) {
          return null;
        }
        else {
          return {
            fileValidator: { valid: false }
          };
        }
      }
      else {
        return null;
      }
    };
  }
}